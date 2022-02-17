package com.example.snippet

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun Timer() {
    var value by remember { mutableStateOf(10) }
    var isTimerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = value, key2 = isTimerRunning, block = {
        if (value > 0 && isTimerRunning) {
            delay(1000)
            value -= 1
            isTimerRunning = value > 0
            Log.d("TAG", "Timer: $value $isTimerRunning")
        }
    })

    Column() {
        Text(text = "$value")
        Button(onClick = {
            isTimerRunning = true
            value = 10
        }) {

        }
    }

}