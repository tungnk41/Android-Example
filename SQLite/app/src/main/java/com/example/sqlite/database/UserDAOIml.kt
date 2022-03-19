package com.example.sqlite.database

import android.database.Cursor
import android.util.Log
import com.example.sqlite.model.User
import java.util.*


class UserDAOIml : UserDAO {
    override fun insertUser(user: User) {
        UserDatabase.instance?._insertionEntity(user)
    }

    override fun updateUser(user: User) {
        UserDatabase.instance?._updateEntity(user)
    }

    override fun isUserExist(user: User): Boolean {
        val cursor: Cursor? = UserDatabase.instance?._getEntity(user)
        val isExist = cursor?.count != 0
        cursor?.close()

        return isExist
    }

    override fun deleteUser(user: User) {
        UserDatabase.instance?._deleteEntity(user);
    }

    override fun getListUser(): List<User> {
        val listUser: MutableList<User> = ArrayList()
        val cursor: Cursor? = UserDatabase.instance?._getAllEntity()
        if (cursor?.moveToFirst() == true) {
            do {
                val user = User()
                user.id = cursor.getLong(cursor.getColumnIndex(UserDatabase.COLUMN_ID)).toInt()
                user.name = cursor.getString(cursor.getColumnIndex(UserDatabase.COLUMN_NAME))
                user.address = cursor.getString(cursor.getColumnIndex(UserDatabase.COLUMN_ADDRESS))
                listUser.add(user)
                Log.d(
                    "TAG",
                    "testDB: username " + user.name + " address " + user.address + " id " + user.id
                )
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return listUser
    }

    override fun deleteAll() {
        UserDatabase.instance?._dropTable();
    }
}