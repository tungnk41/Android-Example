package com.example.sqlite.database

import com.example.sqlite.model.User

interface UserDAO {

    fun insertUser(user: User)

    //Update name,address base on ID
    fun updateUser(user: User)

    fun isUserExist(user: User): Boolean

    fun deleteUser(user: User)

    fun getListUser(): List<User>

    fun deleteAll()
}