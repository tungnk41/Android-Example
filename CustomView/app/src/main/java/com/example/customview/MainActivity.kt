package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var emotionView: EmotionView
    private lateinit var progressBarView: ProgressBarView
    private lateinit var btnButton : Button
    private var pbValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emotionView = findViewById(R.id.emotionView)
        progressBarView = findViewById(R.id.progressBarView)
        btnButton = findViewById(R.id.btnButton)

        btnButton.setOnClickListener {
//            emotionView.invalidate()
            pbValue += 10
            if(pbValue > 100) {
                pbValue = 0
            }
            progressBarView.setValue(pbValue)
        }
    }
}