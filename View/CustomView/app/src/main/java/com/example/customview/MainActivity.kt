package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var emotionView: EmotionView
    private lateinit var btnButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emotionView = findViewById(R.id.emotionView)
        btnButton = findViewById(R.id.btnButton)

        btnButton.setOnClickListener {
            emotionView.invalidate()
        }
    }
}