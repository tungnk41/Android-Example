package com.example.multilanguage

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var btnSetLangEn: Button
    private lateinit var btnSetLangVi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSetLangEn = findViewById(R.id.btnSetLangEn)
        btnSetLangVi = findViewById(R.id.btnSetLangVi)

        btnSetLangEn.setOnClickListener {

        }
        btnSetLangVi.setOnClickListener {

        }
    }

    private fun setAppLanguage(context: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration()
    }

    inner class ContextWrapper(var context: Context): android.content.ContextWrapper(context) {
        val res = context.resources
        val config = res.configuration

        fun wrap(context: Context,locale: Locale) : ContextWrapper {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
                this.context = context.createConfigurationContext(config)
            }
            else {
                config.locale = locale
                res.updateConfiguration(config,res.displayMetrics)
            }
            return ContextWrapper(context)
        }
    }
}