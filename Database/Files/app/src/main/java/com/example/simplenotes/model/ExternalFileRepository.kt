package com.example.simplenotes.model

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ExternalFileRepository(var context : Context): NoteRepository   {
    override fun addNote(note: Note): Boolean {
        if(isExternalStorageWritable()){
            FileOutputStream(getNoteFile(note.fileName)).use {
                it.write(note.noteText.toByteArray())
            }
        }
        return true
    }

    override fun deleteNote(fileName: String): Boolean {
        try {
            if(isExternalStorageWritable()){
                getNoteFile(fileName).delete()
            }
        }catch (e: Exception){
            return false
        }
        return true
    }

    override fun updateNote(note: Note) {
        TODO("Not yet implemented")
    }

    override fun getNote(fileName: String): Note {
        var note : Note = Note(fileName,"")
        if(isExternalStorageReadable()){
            FileInputStream(getNoteFile(fileName)).use {
                val text = it.bufferedReader().use {
                    it.readText()
                }
                note.noteText = text
            }
        }

        return note
    }

    private fun getDirectory() : File? = context.getExternalFilesDir(null)

    private fun getNoteFile(fileName: String) : File {
        return File(getDirectory(),fileName)
    }


    private fun isExternalStorageWritable() : Boolean{
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable() : Boolean{
        return Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

}