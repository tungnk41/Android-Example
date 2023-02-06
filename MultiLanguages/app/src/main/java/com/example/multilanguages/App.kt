package com.example.multilanguages

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.multilanguages.LocaleManager.LocaleManager
import java.util.*

class App: Application() {

    companion object {
        lateinit var localeManager: LocaleManager
    }

    override fun onCreate() {
        super.onCreate()
        localeManager = LocaleManager(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }
}