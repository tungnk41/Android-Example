package com.example.sqldelight.dao.impl

import com.database.sqldelight.AppDatabase
import com.example.sqldelight.dao.INoteDAO
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.Note
import database.NoteQueries
import kotlinx.coroutines.flow.Flow

class NoteDAO(appDatabase: AppDatabase) : INoteDAO {
    val queries: NoteQueries = appDatabase.noteQueries


    override suspend fun findById(id: Long):Note? {
         return queries.findById(id = id).executeAsOneOrNull()
    }

    override fun findAll(user_id: Long): List<Note> {
        return queries.findAll(user_id).executeAsList()
    }

    override suspend fun deleteById(id: Long) {
        queries.deleteById(id)
    }

    override suspend fun delete(user_id: Long) {
        queries.delete(user_id)
    }

    override suspend fun insert(title: String, content: String,user_id : Long): Long {
        queries.insert(id = null, title = title, content = content,user_id = user_id)
        return queries.insert_id().executeAsOne()
    }

    override suspend fun update(id: Long,title: String, content: String) {
        queries.update(id = id,title = title, content = content)
    }
}