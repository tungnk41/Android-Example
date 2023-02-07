package com.example.multilanguages.LocaleManager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION_CODES
import java.lang.ref.WeakReference
import java.util.*


object LocaleManager {
    private const val CURRENT_LANGUAGE = LanguageVariant.SYSTEM

    private var weakContext: WeakReference<Context>?= null
    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        weakContext = WeakReference(context.applicationContext)
        sharedPreferences = context.getSharedPreferences("LocaleManager", MODE_PRIVATE)
    }


    private fun getPersistLanguage(): String {
        return sharedPreferences?.getString(CURRENT_LANGUAGE, LanguageVariant.SYSTEM) ?: LanguageVariant.SYSTEM
    }

    fun attachContext(context: Context?): Context? {
        return if (context != null) updateLocaleResources(context, getPersistLanguage()) else context
    }

    fun setLocale(@Language language: String) {
        persistLanguage(language)
        weakContext?.get()?.let {
            updateLocaleResources(it, language)
        }
    }

    private fun updateLocaleResources(sourceContext: Context, @Language language: String): Context {
        var context = sourceContext
        val systemConfig = Resources.getSystem().configuration
        val locale = if (language == LanguageVariant.SYSTEM) Utils.getSystemLocale(systemConfig) else Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = Configuration(resources.configuration)

        if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        // The modified context
        return context
    }


    private fun persistLanguage(language: String): Boolean {
        return sharedPreferences?.edit()?.putString(CURRENT_LANGUAGE, language)?.commit() ?: false
    }
}