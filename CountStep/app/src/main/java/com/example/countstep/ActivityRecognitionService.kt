package com.example.countstep

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.math.RoundingMode


const val ACTION_START_TRACKING_SERVICE = "ACTION_START_TRACKING_SERVICE"
const val ACTION_STOP_TRACKING_SERVICE = "ACTION_STOP_TRACKING_SERVICE"
const val ACTION_RESET_TRACKING_SERVICE = "ACTION_RESET_TRACKING_SERVICE"
const val ACTION_ACTIVITY_STATE_TRACKING_SERVICE = "ACTION_ACTIVITY_STATE_TRACKING_SERVICE"

class ActivityRecognitionService: Service() {

    companion object {
        private const val DETECTED_ACTIVITY_CHANNEL_ID: String = "detected_activity_channel_id"
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES_GPS: Float = 5f // 5m
        private const val MIN_TIME_BETWEEN_UPDATES_GPS: Long = (1000 * 3).toLong()// 3s
        private const val MIN_TIME_BETWEEN_UPDATES_STEP_SENSOR: Int = SensorManager.SENSOR_DELAY_NORMAL
    }
    private var serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private var isRunning: Boolean = false
    private var stateActivity: Int = DetectedActivity.UNKNOWN
    private val transitions = mutableListOf<ActivityTransition>()
    private var sensorManager: SensorManager? = null
    private var initSteps = 0
    private var prevSteps = 0
    private var steps = 0
    private var location: Pair<Location,Long>? = null
    private var prevLocation: Pair<Location,Long>? = null
    private var distance: Double = 0.0 // m
    private var prevDistance: Double = 0.0 // m
    private var velocity: Double = 0.0 // m/s
    private var locationManager: LocationManager? = null

    private var prevSensorTimestamp: Long = 0
    private var currentSensorTimestamp: Long = 0

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    if(initSteps == 0) {
                        initSteps = event.values[0].toInt()
                    }

                    prevSteps = steps
                    steps = event.values[0].toInt() - initSteps
                    sendDataToBroadcast(ACTION_STEPS_RECEIVER, steps)

                    prevSensorTimestamp = currentSensorTimestamp
                    currentSensorTimestamp = System.currentTimeMillis()
                    sendDataToBroadcast(ACTION_DISTANCE_SENSOR_RECEIVER, calculateDistanceSensor(steps))
                    sendDataToBroadcast(ACTION_VELOCITY_SENSOR_RECEIVER, calculateVelocitySensor(prevSteps,steps))
                    Log.d(TAG, "onSensorChanged: " + steps)
                }
        }
        override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {
            Log.d(TAG, "onAccuracyChanged: " + p1)
        }
    }
    private val listener = LocationListener { newLocation ->
        if(isValidUpdateStateActivity()) {
            prevLocation = location
            location = Pair(newLocation,System.currentTimeMillis())

            if(location != null && prevLocation != null) {
                prevDistance = distance
                distance += calculateDistanceGps(prevLocation!!.first,location!!.first)
                velocity = calculateVelocityGps(prevDistance,prevLocation!!.second,distance, location!!.second)
            }
            else {
                distance = 0.0
                velocity = 0.0
            }
            sendDataToBroadcast(ACTION_DISTANCE_GPS_RECEIVER, distance)
            sendDataToBroadcast(ACTION_VELOCITY_GPS_RECEIVER, velocity)
        }
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            Log.d("TAG", "onStartCommand: " + intent.action)
            when(intent.action){
                ACTION_START_TRACKING_SERVICE -> startActivityTracking()
                ACTION_STOP_TRACKING_SERVICE -> stopActivityTracking()
                ACTION_RESET_TRACKING_SERVICE -> resetDataTracking()
                ACTION_ACTIVITY_STATE_TRACKING_SERVICE -> setStateActivity(intent.getIntExtra("ACTION_ACTIVITY_STATE_TRACKING_SERVICE", DetectedActivity.UNKNOWN))
            }
        }
        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if(isRunning){
            stopForeground(true)
        }
        stopSelf()
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        release()
        super.onDestroy()
    }

    private fun init() {
        initStepCounter()
        initTransitionActivity()
        initLocationTracker()
    }

    private fun release() {
        sensorManager?.unregisterListener(sensorEventListener)
        locationManager?.removeUpdates(listener)
        serviceJob?.cancel()
        sensorManager = null
        locationManager = null
    }

    private fun initLocationTracker() {
        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        val isGpsEnable = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(isGpsEnable == true && locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BETWEEN_UPDATES_GPS, MIN_DISTANCE_CHANGE_FOR_UPDATES_GPS, listener)
        }
    }

    private fun initStepCounter() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(sensorManager == null) {
            Toast.makeText(this, "Cannot find sensor", Toast.LENGTH_SHORT).show()
        }
        else {
            sensorManager?.registerListener(sensorEventListener,stepSensor, MIN_TIME_BETWEEN_UPDATES_STEP_SENSOR)
        }
    }

    private fun initTransitionActivity() {
        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.RUNNING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()
        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.RUNNING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()
        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()
        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()
        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()
        transitions +=
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()
    }

    private fun startActivityTransitionTracking() {
        val request = ActivityTransitionRequest(transitions)
        val task = ActivityRecognitionClient(this).requestActivityTransitionUpdates(request, ActivityRecognitionReceiver.getPendingIntentActivityTransition(this))
        task.addOnSuccessListener {
            Log.d("TAG","Register Activity Transition Tracking Success")
        }
        task.addOnFailureListener { e: Exception ->
            Log.d("TAG","Register Activity Transition Tracking Fail")
        }
    }

    private fun stopActivityTransitionTracking() {
        ActivityRecognitionClient(this).removeActivityTransitionUpdates(ActivityRecognitionReceiver.getPendingIntentActivityTransition(this))
            .addOnSuccessListener {
                Log.d("TAG","UnRegister Activity Transition Tracking Success")
            }
            .addOnFailureListener {
                Log.d("TAG","UnRegister Activity Transition Tracking Fail")
            }
    }

    private fun startActivityTracking() {
        val task = ActivityRecognitionClient(this).requestActivityUpdates(1000, ActivityRecognitionReceiver.getPendingIntentActivity(this))
        task.addOnSuccessListener {
            Log.d("TAG","Register Activity Tracking Success")
            isRunning = true
            startForegroundService()
        }
        task.addOnFailureListener { e: Exception ->
            Log.d("TAG","Register Activity Tracking Fail")
        }
    }

    private fun stopActivityTracking() {
        ActivityRecognitionClient(this).removeActivityUpdates(ActivityRecognitionReceiver.getPendingIntentActivity(this))
            .addOnSuccessListener {
                stopForeground(true)
                isRunning = false
            }
            .addOnFailureListener {
                Log.d("TAG","UnRegister Activity Tracking Fail")
            }
    }

    private fun resetDataTracking() {
        prevSteps = steps
        sendDataToBroadcast(ACTION_STEPS_RECEIVER,0)
    }

    private fun setStateActivity(state: Int) {
        stateActivity = state
        if(!isValidUpdateStateActivity()) {
            sendDataToBroadcast(ACTION_VELOCITY_GPS_RECEIVER,0)
            sendDataToBroadcast(ACTION_VELOCITY_SENSOR_RECEIVER,0)
        }
    }

    private fun startForegroundService() {
        createNotificationChannel(this)
        val builder = NotificationCompat.Builder(this, DETECTED_ACTIVITY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Detect Activity")
            .setContentText("Running...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        startForeground(1,builder.build())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "detected_activity_channel_name"
            val descriptionText = "detected_activity_channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DETECTED_ACTIVITY_CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableVibration(false)
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendDataToBroadcast(action: String, data: Any? = null) {
        when(action) {
            ACTION_STEPS_RECEIVER -> {
                sendBroadcast(Intent(ACTION_STEPS_RECEIVER).apply { putExtra("ACTION_STEPS_RECEIVER",data.toString()) })
            }
            ACTION_DISTANCE_GPS_RECEIVER -> {
                sendBroadcast(Intent(ACTION_DISTANCE_GPS_RECEIVER).apply { putExtra("ACTION_DISTANCE_GPS_RECEIVER",data.toString()) })
            }
            ACTION_VELOCITY_GPS_RECEIVER -> {
                sendBroadcast(Intent(ACTION_VELOCITY_GPS_RECEIVER).apply { putExtra("ACTION_VELOCITY_GPS_RECEIVER",data.toString()) })
            }
            ACTION_DISTANCE_SENSOR_RECEIVER -> {
                sendBroadcast(Intent(ACTION_DISTANCE_SENSOR_RECEIVER).apply { putExtra("ACTION_DISTANCE_SENSOR_RECEIVER",data.toString()) })
            }
            ACTION_VELOCITY_SENSOR_RECEIVER -> {
                sendBroadcast(Intent(ACTION_VELOCITY_SENSOR_RECEIVER).apply { putExtra("ACTION_VELOCITY_SENSOR_RECEIVER",data.toString()) })
            }
        }
    }

    private fun calculateDistanceGps(prevLocation: Location, location: Location ): Double {
        //Using Haversine formula
//        val R: Double = 6371.0 // Radius of Earth in Km
//        val dlat = Math.toRadians(location.latitude - prevLocation.latitude)
//        val dlon = Math.toRadians(location.longitude - prevLocation.longitude)
//        val a: Double = Math.pow(Math.sin(dlat/2),2.0) + Math.cos(Math.toRadians(prevLocation.latitude))* Math.cos(Math.toRadians(location.latitude)) * Math.pow(Math.sin(dlon/2),2.0)
//        val c: Double = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
//        val dist: Double = (R * c) * 1000 // km -> m

        var dist = prevLocation.distanceTo(location).toDouble()
        dist = if(dist > 30) 0.0 else dist
        return BigDecimal(dist).setScale(2, RoundingMode.CEILING).toDouble()
    }

    private fun calculateVelocityGps(prevDistance: Double, prevTime: Long, distance: Double, time: Long) : Double {
        val timeInSecond = ((time - prevTime)/1000)
        var velocity = (((distance - prevDistance)/timeInSecond))
        velocity = if(velocity < 0) 0.0 else velocity
        return BigDecimal(velocity).setScale(1, RoundingMode.CEILING).toDouble()
    }

    private fun calculateDistanceSensor(steps: Int) : Double {
        val stride = 0.425 // 1m70 / 4
        val dist = steps * stride
        return BigDecimal(dist).setScale(1, RoundingMode.CEILING).toDouble()
    }

    private fun calculateVelocitySensor(prevSteps : Int,steps: Int) : Double {
        val dTime = (currentSensorTimestamp - prevSensorTimestamp) / 1000
        if(dTime < 1) return  0.0

        val v = ((steps - prevSteps) * 0.425) / dTime
        return BigDecimal(v).setScale(1, RoundingMode.CEILING).toDouble()
    }

    private fun isValidUpdateStateActivity(): Boolean {
        return (stateActivity == DetectedActivity.WALKING || stateActivity == DetectedActivity.RUNNING)
    }

}