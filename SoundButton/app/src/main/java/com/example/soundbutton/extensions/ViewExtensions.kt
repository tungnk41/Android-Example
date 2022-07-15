package com.example.soundbutton.extensions

import android.os.SystemClock
import android.view.View
import com.example.soundbutton.SoundPoolPlayer

fun View.setOnSoundClickListener(onClick: () -> Unit) {
    setOnClickListener {
        SoundPoolPlayer.stop()
        SoundPoolPlayer.play()
        onClick()
    }
}