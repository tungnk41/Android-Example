package com.example.multilanguages

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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
            refeshLayout()
        }

        btnSetLangVi.setOnClickListener {
            setAppLocale("vi")
            refeshLayout()
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

    private fun refeshLayout() {
        val intent = getIntent()
        finish();
        startActivity(intent)
    }
}