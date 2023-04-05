package com.android.compositeview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var compositeView: CompositeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeView = findViewById(R.id.compositeView)

        compositeView.onClicked = {
            Log.d("TAG", "onCreate: ")
        }
    }
}