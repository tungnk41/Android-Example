package com.example.simplenotes.model

import android.content.Context
import android.util.Log
import java.io.File
import java.lang.Exception

class InternalFileRepository(var context : Context) : NoteRepository {
    override fun addNote(note: Note) : Boolean {
        try {
            context.openFileOutput(note.fileName,Context.MODE_PRIVATE).use { outStream ->
                outStream.write(note.noteText.toByteArray())
            }
        }catch (e : Exception){
            return false
        }
        return true
    }

    override fun deleteNote(fileName: String) : Boolean{
        try {
            getNoteFile(fileName).delete()
        }catch (e : Exception){
            return false
        }
        return true
    }

    override fun updateNote(note: Note) {

    }

    override fun getNote(fileName: String): Note {
        var note = Note(fileName,"")

        try{
            context.openFileInput(fileName)?.use { inStream ->

                //Read all text of block to string
                val text =  inStream.bufferedReader().use {
                    it.readText()
                }
                note.noteText = text
            }
        }catch (e : Exception){
            Log.d("TAG", "getNote Fail ")
        }


        return note
    }

    private fun getDirectory() : String = context.filesDir.absolutePath

    private fun getNoteFile(fileName: String) : File {
        return File(getDirectory(),fileName)
    }

}