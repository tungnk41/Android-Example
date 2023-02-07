package com.example.multilanguages

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.multilanguages.LocaleManager.LocaleManager
import java.util.*

class App: Application() {


    override fun onCreate() {
        super.onCreate()
        LocaleManager.init(this)
    }

}