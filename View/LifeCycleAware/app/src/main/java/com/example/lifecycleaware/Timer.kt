package com.example.lifecycleaware

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class Timer : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start(){
        Log.d(TAG, "Timer start: ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop(){
        Log.d(TAG, "Timer stop: ")
    }
}