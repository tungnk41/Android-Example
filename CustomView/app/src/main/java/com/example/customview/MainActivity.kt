package com.example.customview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.customview.extensions.setGradientColor
import com.example.customview.extensions.setStrokeColor

class MainActivity : AppCompatActivity() {
    private lateinit var emotionView: EmotionView
    private lateinit var progressBarView: ProgressBarView
    private lateinit var btnButton : Button
    private lateinit var tvTextView: TextView
    private lateinit var tvOutline: OutlineTextView
    private var pbValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emotionView = findViewById(R.id.emotionView)
        progressBarView = findViewById(R.id.progressBarView)
        btnButton = findViewById(R.id.btnButton)
        tvTextView = findViewById(R.id.tvTextView)
        tvOutline = findViewById(R.id.tvOutline)

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