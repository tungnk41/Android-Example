package com.example.sqldelight

import android.app.Application
import android.content.Context
import com.database.sqldelight.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver

class App : Application() {
    companion object{
        lateinit var context : Context
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        database = AppDatabase(AndroidSqliteDriver(AppDatabase.Schema,this,"todo.db"))
    }
}