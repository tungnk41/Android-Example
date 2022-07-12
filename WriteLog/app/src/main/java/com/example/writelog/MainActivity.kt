package com.example.writelog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var btnButton: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnButton = findViewById(R.id.btnButton)

        Timber.d("AAAAAAA")
        Timber.v("BBBBBBBBB")
        btnButton.setOnClickListener {
            val a = 10 / 0
        }
    }

}