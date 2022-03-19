package com.example.sqldelight.dao.impl

import com.database.sqldelight.AppDatabase
import com.example.sqldelight.dao.IUserDAO
import database.User
import database.UserQueries
import kotlinx.coroutines.flow.Flow

class UserDAO(appDatabase: AppDatabase) : IUserDAO {
    val queries : UserQueries = appDatabase.userQueries

    override suspend fun findByNameAndPassword(username: String,password: String): User? {
        return queries.findByNameAndPassword(username,password).executeAsOneOrNull()
    }

    override fun findAll(): Flow<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(username: String, password: String): Long {
        queries.insert(null,username,password)
        return queries.insert_id().executeAsOne()
    }

    override suspend fun update(id: Long, username: String, password: String) {
        TODO("Not yet implemented")
    }

}