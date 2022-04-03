package com.example.countstep

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ContentInfoCompat
import com.google.android.gms.location.*

class MainActivity : AppCompatActivity() {
    private lateinit var btnReset: Button
    private lateinit var btnStartTracking: Button
    private lateinit var btnStopTracking: Button
    private lateinit var tvStep: TextView
    private lateinit var tvActivity: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvVelocity: TextView
    private lateinit var broadcastReceiver: ActivityRecognitionReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStep = findViewById(R.id.tvStep)
        btnReset = findViewById(R.id.btnReset)
        btnStartTracking = findViewById(R.id.btnStatTracking)
        btnStopTracking = findViewById(R.id.btnStopTracking)
        tvActivity = findViewById(R.id.tvActivity)
        tvDistance = findViewById(R.id.tvDistance)
        tvVelocity = findViewById(R.id.tvVelocity)

        broadcastReceiver = ActivityRecognitionReceiver()
        broadcastReceiver.setIFaceUpdateUI(object : ActivityRecognitionReceiver.IFaceActivity{
            override fun onActivityTransitionChanged(activity: String, transition: String) {
                tvActivity.text = activity
            }
            override fun onActivityChanged(activity: String) {
                tvActivity.text = activity
            }

            override fun onDistanceChanged(distance: String) {
                tvDistance.text = distance
            }

            override fun onVelocityChanged(velocity: String) {
                tvVelocity.text = velocity
            }

            override fun onStepChanged(steps: String) {
                tvStep.text = steps
            }
        })

        btnReset.setOnClickListener {
            startService(Intent(this, ActivityRecognitionService::class.java).apply { action = ACTION_RESET_TRACKING_SERVICE })
        }
        btnStartTracking.setOnClickListener {
            startService(Intent(this, ActivityRecognitionService::class.java).apply { action = ACTION_START_TRACKING_SERVICE })
        }
        btnStopTracking.setOnClickListener {
            startService(Intent(this, ActivityRecognitionService::class.java).apply { action = ACTION_STOP_TRACKING_SERVICE })
        }
        checkPermission()
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter().apply {
            addAction(ACTION_ACTIVITY_TRANSITIONS_RECEIVER)
            addAction(ACTION_ACTIVITY_RECEIVER)
            addAction(ACTION_DISTANCE_RECEIVER)
            addAction(ACTION_STEPS_RECEIVER)
        })
    }

    override fun onStop() {
        unregisterReceiver(broadcastReceiver)
        super.onStop()
    }


    private fun checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val requestCode = 200
            val perms = arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION, android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION)

            var isNeedToRequest = false
            perms.forEach { permission ->
                if(ActivityCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED ){
                    isNeedToRequest = true
                }
            }
            if(isNeedToRequest) {
                this.requestPermissions(perms,requestCode)
            }
        }
    }

}