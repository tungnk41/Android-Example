package com.example.multilanguages

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.multilanguages.LocaleManager.LanguageVariant
import com.example.multilanguages.LocaleManager.LocaleManager
import com.example.multilanguages.LocaleManager.Utils


class MainActivity : AppCompatActivity() {

    private lateinit var btnSetLangEn: Button
    private lateinit var btnSetLangVi: Button

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.attachContext(newBase))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.resetActivityTitle(this)
        btnSetLangEn = findViewById(R.id.btnSetLangEn)
        btnSetLangVi = findViewById(R.id.btnSetLangVi)

        btnSetLangEn.setOnClickListener {
            LocaleManager.setLocale(LanguageVariant.ENGLISH)
            recreate()
        }

        btnSetLangVi.setOnClickListener {
            LocaleManager.setLocale(LanguageVariant.VIETNAMESE)
            recreate()
        }
    }

}