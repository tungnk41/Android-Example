package com.example.crashlog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

/*
Top crash report
* ACRA
* Firebase crash report
* */
class MainActivity : AppCompatActivity() {
    private lateinit var btnCrash : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCrash = findViewById(R.id.btnCrash)

        btnCrash.setOnClickListener {
            //throw RuntimeException("Test Crash")

            val list = listOf(3)
            list[5]
        }

    }
}