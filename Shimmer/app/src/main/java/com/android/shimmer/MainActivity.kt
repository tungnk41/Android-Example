package com.android.shimmer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.facebook.shimmer.ShimmerFrameLayout

class MainActivity : AppCompatActivity() {

    private lateinit var container : ShimmerFrameLayout
    private lateinit var btnStart : Button
    private lateinit var btnStop : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.container)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)

        btnStart.setOnClickListener {
            container.startShimmerAnimation()
        }

        btnStop.setOnClickListener {
            container.stopShimmerAnimation()
        }
    }
}