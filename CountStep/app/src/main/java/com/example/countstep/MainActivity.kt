package com.example.countstep

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnReset: Button
    private lateinit var tvStep: TextView

    private var sensorManager: SensorManager? = null
    private var isRunning = false
    private var totalSteps = 0f
    private var prevSteps = 0f

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            Log.d("TAG", "onSensorChanged: ")
            event?.let {
                totalSteps = event.values[0]
                val steps = totalSteps - prevSteps
                tvStep.setText(steps.toString())
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStep = findViewById(R.id.tvStep)
        btnReset = findViewById(R.id.btnReset)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(sensorManager == null) {
            Toast.makeText(this, "Cannot find sensor", Toast.LENGTH_SHORT).show()
        }
        else {
            sensorManager?.registerListener(sensorEventListener,stepSensor,SensorManager.SENSOR_DELAY_UI)
        }

        btnReset.setOnClickListener {
            prevSteps = totalSteps
            tvStep.setText("0")
        }

        checkPermission()
    }

    private fun checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val requestCode = 200
            val perms = arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION)

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

    override fun onDestroy() {
        super.onDestroy()
        sensorManager?.unregisterListener(sensorEventListener)
    }
}