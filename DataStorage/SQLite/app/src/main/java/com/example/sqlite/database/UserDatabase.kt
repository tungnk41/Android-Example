package com.example.sqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlite.App
import com.example.sqlite.model.User


class UserDatabase private constructor(context: Context) {
    val userDAO: UserDAO
    val dbHelper: UserSQLiteHelper

    class UserSQLiteHelper(context: Context?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(database: SQLiteDatabase) {
            updateDatabase(database,0,1)
        }

        override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            updateDatabase(database,oldVersion,newVersion)
        }

        fun updateDatabase(database : SQLiteDatabase,oldVersion: Int, newVersion: Int ) {
            if (oldVersion == 0 && newVersion == 1) {
                database.execSQL(
                    "CREATE TABLE " + TABLE_NAME + "( "
                            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + COLUMN_NAME + " TEXT, "
                            + COLUMN_ADDRESS + " TEXT )"
                )
            }
            if (oldVersion == 1 && newVersion == 2) {
                database.execSQL(
                    "ALTER TABLE " + TABLE_NAME + " ADD COLUMN "
                            + COLUMN_MID_NAME + " TEXT )"
                )
            }
        }
    }

    init {
        dbHelper = UserSQLiteHelper(context)
        userDAO = UserDAOIml()
    }

    companion object {
        const val TABLE_NAME = "user"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_MID_NAME = "midname"
        const val COLUMN_ADDRESS = "address"
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 1


        @get:Synchronized
        var instance: UserDatabase? = null
            get() {
                if (field == null) {
                    field = UserDatabase(App.getApplicationContext())
                }
                return field
            }
            private set
    }

    fun _insertionEntity(user: User): Long {
        val database = dbHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, user.name)
        contentValues.put(COLUMN_ADDRESS, user.address)
        return database.insert(TABLE_NAME, null, contentValues)
    }

    fun _getEntity(user: User): Cursor {
        val database = dbHelper.readableDatabase
        return database.rawQuery(
            "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=? " + " AND " + COLUMN_ADDRESS + "=?",
            arrayOf(user.name, user.address)
        )
    }

    fun _updateEntity(user: User) {
        val database = dbHelper.writableDatabase
        val cursor = database.rawQuery(
            "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME + "=? , " + COLUMN_ADDRESS + "=? " + "  WHERE " + COLUMN_ID + "=?",
            arrayOf(user.name, user.address, user.id.toString())
        )
        cursor.close()
    }

    fun _deleteEntity(user: User) {
        val database = dbHelper.writableDatabase
        val cursor = database.rawQuery(
            "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=?" + " AND " + COLUMN_ADDRESS + "=?",
            arrayOf(user.name, user.address)
        )
        cursor.close()
    }

    fun _getAllEntity(): Cursor {
        val database = dbHelper.readableDatabase
        return database.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    fun _dropTable() {
        val database = dbHelper.writableDatabase
        database.execSQL("DROP TABLE " + TABLE_NAME)
    }

    fun _deleteTable() {
        val database = dbHelper.writableDatabase
        database.execSQL("DELETE FROM " + TABLE_NAME)
    }

    fun close() {
        dbHelper.close()
    }


}