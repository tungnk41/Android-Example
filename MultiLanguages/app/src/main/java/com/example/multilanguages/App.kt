package com.example.multilanguages

import android.app.Application
import android.content.res.Configuration
import java.util.*

class App: Application() {
    private var locale: Locale? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

}