package com.example.simplenotes.model

import android.content.Context
import android.os.Environment
import android.provider.Settings
import android.util.Log
import java.io.*
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class EncryptFileRepository(var context: Context) : NoteRepository {
    private val passwordString : String = "abc"

    override fun addNote(note: Note) : Boolean{
        if(isExternalStorageWritable()){
            ObjectOutputStream(getNoteFileOutputStream(fileName = note.fileName)).use {
                it.writeObject(encrypt(note.noteText.toByteArray()))
            }
        }else{
            return false
        }
        return true
    }

    override fun deleteNote(fileName: String): Boolean {
        if(isExternalStorageWritable()){
            getNoteFile(fileName).delete()
        }else{
            return false
        }
        return true
    }

    override fun updateNote(note: Note) {
        TODO("Not yet implemented")
    }

    override fun getNote(fileName: String): Note {
        val note = Note(fileName,"")
        try {
            if(isExternalStorageReadable()){
                ObjectInputStream(getNoteFileInputStream(fileName)).use {
                    val mapFile = it.readObject() as HashMap<String, ByteArray>
                    val data = decrypt(mapFile)
                    data?.let { note.noteText = String(data) }
                }
            }
        }catch (e: Exception){

        }

        return note
    }


    private fun decrypt(map : HashMap<String, ByteArray>) : ByteArray?{

        var data : ByteArray? = null
        try{
            val salt = map["salt"]
            val iv = map["iv"]
            val dataEncrypted = map["dataEncrypted"]

            // 1 regenerate key from password
            val password = passwordString.toCharArray()
            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            // 2 Decrypt
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            data = cipher.doFinal(dataEncrypted)
        }catch (e: Exception){

        }
        return data
    }

    private fun encrypt(byteArray : ByteArray) : HashMap<String,ByteArray>{
        /*
        * Using AES and Password-Based Key Derivation
        *
        * produce a key based on the user’s password using Password-Based Key Derivation Function (PBKDF2)
        * PBKDF2 produces a key from a password by hashing it over many times with salt, the derived key will be
          unique even if two or more users in the system used the same password.
        * */
        var map = HashMap<String,ByteArray>()

        try{
            //Create random salt byte array
            val random = SecureRandom()
            val salt= ByteArray(256)
            random.nextBytes(salt)

            val password = passwordString.toCharArray()
            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            //Create initialization vector
            val ivRandom = SecureRandom()
            val iv = ByteArray(16)
            ivRandom.nextBytes(iv)
            val ivSpec = IvParameterSpec(iv)


            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val dataEncrypted = cipher.doFinal(byteArray)

            map["salt"] = salt
            map["iv"] = iv
            map["dataEncrypted"] = dataEncrypted

        }catch(e: Exception){

        }
        return map
    }

    private fun getDirectory() : String = context.filesDir.absolutePath

    private fun getNoteFile(fileName: String) : File {
        return File(getDirectory(),fileName)
    }

    private fun getNoteFileOutputStream(fileName: String) : FileOutputStream{
        return FileOutputStream(getNoteFile(fileName))
    }

    private fun getNoteFileInputStream(fileName: String) : FileInputStream{
        return FileInputStream(getNoteFile(fileName))
    }
    private fun isExternalStorageWritable() : Boolean{
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable() : Boolean{
        return Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
}