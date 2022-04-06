package com.example.countstep

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity

const val ACTION_ACTIVITY_TRANSITIONS_RECEIVER = "ACTION_ACTIVITY_TRANSITIONS_RECEIVER"
const val ACTION_ACTIVITY_RECEIVER = "ACTION_ACTIVITY_RECEIVER"
const val ACTION_STEPS_RECEIVER = "ACTION_STEPS_RECEIVER"
const val ACTION_DISTANCE_GPS_RECEIVER = "ACTION_DISTANCE_GPS_RECEIVER"
const val ACTION_VELOCITY_GPS_RECEIVER = "ACTION_VELOCITY_GPS_RECEIVER"
const val ACTION_DISTANCE_SENSOR_RECEIVER = "ACTION_DISTANCE_SENSOR_RECEIVER"
const val ACTION_VELOCITY_SENSOR_RECEIVER = "ACTION_VELOCITY_SENSOR_RECEIVER"

class ActivityRecognitionReceiver : BroadcastReceiver() {

    interface IFaceActivity {
        fun onActivityTransitionChanged(activity: String,transition: String)
        fun onActivityChanged(activity: String)
        fun onDistanceGpsChanged(distance: String)
        fun onVelocityGpsChanged(velocity: String)
        fun onDistanceSensorChanged(distance: String)
        fun onVelocitySensorChanged(velocity: String)
        fun onStepChanged(step: String)
    }

    companion object {
        fun getPendingIntentActivityTransition(context: Context): PendingIntent {
            val intent = Intent(ACTION_ACTIVITY_TRANSITIONS_RECEIVER)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        }
        fun getPendingIntentActivity(context: Context): PendingIntent {
            val intent = Intent(ACTION_ACTIVITY_RECEIVER)
            return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_MUTABLE)
        }
        fun getFilterAction(): IntentFilter {
            return IntentFilter().apply {
                addAction(ACTION_ACTIVITY_TRANSITIONS_RECEIVER)
                addAction(ACTION_ACTIVITY_RECEIVER)
                addAction(ACTION_DISTANCE_GPS_RECEIVER)
                addAction(ACTION_VELOCITY_GPS_RECEIVER)
                addAction(ACTION_DISTANCE_SENSOR_RECEIVER)
                addAction(ACTION_VELOCITY_SENSOR_RECEIVER)
                addAction(ACTION_STEPS_RECEIVER)
            }
        }
    }

    private var iFaceActivity: IFaceActivity? =null

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            when(intent.action){
                ACTION_ACTIVITY_TRANSITIONS_RECEIVER -> {
                    if(ActivityTransitionResult.hasResult(intent)) {
                        val result = ActivityTransitionResult.extractResult(intent)!!
                        for(event in result.transitionEvents) {
                            Log.d("TAG", "onReceive ACTION_ACTIVITY_TRANSITIONS_RECEIVER: " + activityToString(event.activityType) + ", " + transitionToString(event.transitionType))
                            iFaceActivity?.onActivityTransitionChanged(activityToString(event.activityType), transitionToString(event.transitionType))
                        }
                    }
                    else {
                        Log.d("TAG", "onReceive ACTION_ACTIVITY_TRANSITIONS_RECEIVER: !hasResult")
                    }
                }
                ACTION_ACTIVITY_RECEIVER -> {
                    val result = ActivityRecognitionResult.extractResult(intent)!!
                    result?.let {
                        val listDetectActivity = it.probableActivities
                        listDetectActivity
                            .filter {
                                it.type == DetectedActivity.STILL ||
                                it.type == DetectedActivity.WALKING ||
                                it.type == DetectedActivity.RUNNING
                            }
                            .filter {
                                it.confidence >= 75
                            }
                            .run {
                                if(isNotEmpty()) {
                                    val stateActivity = this[0]
                                    iFaceActivity?.onActivityChanged(activityToString(stateActivity))
                                    Log.d("TAG", "onReceive: " + context)
                                    context?.startService(Intent(context, ActivityRecognitionService::class.java).apply {
                                        action = ACTION_ACTIVITY_STATE_TRACKING_SERVICE
                                        putExtra(ACTION_ACTIVITY_STATE_TRACKING_SERVICE, stateActivity.type)
                                    })
                                }
                            }
                    }
                }
                ACTION_STEPS_RECEIVER -> {
                    val steps = intent.getStringExtra("ACTION_STEPS_RECEIVER") ?: "0"
                    iFaceActivity?.onStepChanged(steps)
                }
                ACTION_DISTANCE_GPS_RECEIVER -> {
                    val distance = intent.getStringExtra("ACTION_DISTANCE_GPS_RECEIVER") ?: "0"
                    iFaceActivity?.onDistanceGpsChanged(distance)
                }
                ACTION_VELOCITY_GPS_RECEIVER -> {
                    val velocity = intent.getStringExtra("ACTION_VELOCITY_GPS_RECEIVER") ?: "0"
                    iFaceActivity?.onVelocityGpsChanged(velocity)
                }
                ACTION_DISTANCE_SENSOR_RECEIVER -> {
                    Log.d("TAG", "onReceive: ACTION_DISTANCE_SENSOR_RECEIVER")
                    val distance = intent.getStringExtra("ACTION_DISTANCE_SENSOR_RECEIVER") ?: "0"
                    iFaceActivity?.onDistanceSensorChanged(distance)
                }
                ACTION_VELOCITY_SENSOR_RECEIVER -> {
                    Log.d("TAG", "onReceive: ACTION_VELOCITY_SENSOR_RECEIVER")
                    val velocity = intent.getStringExtra("ACTION_VELOCITY_SENSOR_RECEIVER") ?: "0"
                    iFaceActivity?.onVelocitySensorChanged(velocity)
                }
                else -> {
                    Log.d("TAG", "onReceive: ACTION UNKNOWN ")
                    return
                }
            }
        }
    }

    private fun activityToString(detectedActivity: DetectedActivity): String {
        return when(detectedActivity.type) {
            DetectedActivity.IN_VEHICLE -> "IN_VEHICLE"
            DetectedActivity.ON_BICYCLE -> "ON_BICYCLE"
            DetectedActivity.ON_FOOT    -> "ON_FOOT"
            DetectedActivity.STILL      -> "STILL"
            DetectedActivity.UNKNOWN    -> "UNKNOWN"
            DetectedActivity.TILTING    -> "TILTING"
            DetectedActivity.WALKING    -> "WALKING"
            DetectedActivity.RUNNING    -> "RUNNING"
            else -> "UNKNOWN"
        }
    }

    private fun activityToString(activity: Int): String {
        return when(activity) {
            DetectedActivity.IN_VEHICLE -> "IN_VEHICLE"
            DetectedActivity.ON_BICYCLE -> "ON_BICYCLE"
            DetectedActivity.ON_FOOT    -> "ON_FOOT"
            DetectedActivity.STILL      -> "STILL"
            DetectedActivity.UNKNOWN    -> "UNKNOWN"
            DetectedActivity.TILTING    -> "TILTING"
            DetectedActivity.WALKING    -> "WALKING"
            DetectedActivity.RUNNING    -> "RUNNING"
            else -> "UNKNOWN"
        }
    }

    private fun transitionToString(transition: Int): String {
        return when(transition) {
            ActivityTransition.ACTIVITY_TRANSITION_ENTER -> "ENTER"
            ActivityTransition.ACTIVITY_TRANSITION_EXIT -> "EXIT"
            else -> "UNKNOWN"
        }
    }

    fun setIFaceUpdateUI(iFaceActivity: IFaceActivity) {
        this.iFaceActivity = iFaceActivity
    }
}

