package com.example.sqldelight.dao

import database.User
import kotlinx.coroutines.flow.Flow

interface IUserDAO {
    suspend fun findByNameAndPassword(username: String,password: String): User?
    fun findAll() : Flow<List<User>>
    suspend fun delete(id: Long)
    suspend fun insert(username: String, password: String): Long
    suspend fun update(id: Long,username: String, password: String)
}