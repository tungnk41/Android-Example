package com.example.greendao

import android.app.Application
import com.example.greendao.model.DaoMaster
import com.example.greendao.model.DaoSession
import org.greenrobot.greendao.database.Database

class App : Application() {
    private lateinit var daoSession : DaoSession

    override fun onCreate() {
        super.onCreate()
        val helper : DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(this,"user-db")
        val database : Database = helper.getWritableDb()
        daoSession = DaoMaster(database).newSession()
    }

    fun getDaoSession() : DaoSession{
        return daoSession
    }
}