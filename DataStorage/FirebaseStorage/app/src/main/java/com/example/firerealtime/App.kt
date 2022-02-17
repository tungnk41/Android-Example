package com.example.firerealtime

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().apply {
            setPersistenceEnabled(true)
            getReference("posts").keepSynced(true)
        }
    }
}