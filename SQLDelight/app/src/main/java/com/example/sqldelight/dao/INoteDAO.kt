package com.example.sqldelight.dao

import database.Note
import kotlinx.coroutines.flow.Flow
import java.util.*

interface INoteDAO {
    suspend fun findById(id : Long): Note?
    fun findAll(user_id: Long) : List<Note>
    suspend fun deleteById(id: Long)
    suspend fun delete(user_id: Long)
    suspend fun insert(title: String, content: String,user_id: Long): Long
    suspend fun update(id: Long,title: String, content: String)
}