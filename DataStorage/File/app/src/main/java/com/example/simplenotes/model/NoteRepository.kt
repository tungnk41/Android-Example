package com.example.simplenotes.model

interface NoteRepository {
    fun addNote(note : Note) : Boolean
    fun deleteNote(fileName: String) : Boolean
    fun updateNote(note: Note)
    fun getNote(fileName : String) : Note
}