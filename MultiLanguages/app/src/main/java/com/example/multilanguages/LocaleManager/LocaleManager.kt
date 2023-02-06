package com.example.multilanguages.LocaleManager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build.VERSION_CODES
import androidx.annotation.NonNull
import java.util.*

class LocaleManager constructor(context: Context) {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences
    init {
        this.context = context.applicationContext
        sharedPreferences = context.getSharedPreferences("LocaleManager", MODE_PRIVATE)
    }

    companion object {
        const val CURRENT_LANGUAGE = ""
    }



    fun getCurrentLanguage(): String {
        return sharedPreferences.getString(CURRENT_LANGUAGE,LanguageSupport.Language.SYSTEM) ?: LanguageSupport.Language.SYSTEM
    }

    fun setLanguage
                (@LanguageSupport.Language language: String) {
        return updateLocaleResources(context, getLanguage())
    }

    @NonNull
    private fun updateLocaleResources(
        @NonNull context: Context,
        @NonNull language: String?
    ): Context? {
        val systemConfig = Resources.getSystem().configuration
        val locale = if (language == LanguageSupport.Language.SYSTEM) Utils.getSystemLocale(systemConfig) else Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = Configuration(resources.configuration)

        if (Utils.isAtLeastAndroidVersion(VERSION_CODES.JELLY_BEAN_MR1)) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        // The modified context
        return context
    }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String): Boolean {
        return sharedPreferences.edit().putString(CURRENT_LANGUAGE, language).commit()
    }
}