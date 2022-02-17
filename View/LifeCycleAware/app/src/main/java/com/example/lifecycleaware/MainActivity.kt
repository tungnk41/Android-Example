package com.example.lifecycleaware

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry

class MainActivity : AppCompatActivity() {

    private var timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(timer)
        /*
            auto run timer.start() after MainActivity run onStart()
            auto run timer.stop() before MainActivity run onStop()
         */

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity onStart: ")
    }


    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity onStop: ")
    }
}