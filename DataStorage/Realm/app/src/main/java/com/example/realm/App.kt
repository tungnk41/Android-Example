package com.example.realm

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        realmInit()
    }

    fun realmInit(){
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("realm.db")
            .allowWritesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(config)
    }


}