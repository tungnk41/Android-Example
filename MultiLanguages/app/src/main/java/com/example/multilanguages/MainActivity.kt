package com.example.multilanguages

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnSetLangEn: Button
    private lateinit var btnSetLangVi: Button

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ContextWrapper(newBase?.setAppLocale("en")))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSetLangEn = findViewById(R.id.btnSetLangEn)
        btnSetLangVi = findViewById(R.id.btnSetLangVi)

        btnSetLangEn.setOnClickListener {
            setAppLocale("en")
            recreate()
        }

        btnSetLangVi.setOnClickListener {
            setAppLocale("vi")
            recreate()
        }
    }

    fun Context.setAppLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return createConfigurationContext(config)
    }
}