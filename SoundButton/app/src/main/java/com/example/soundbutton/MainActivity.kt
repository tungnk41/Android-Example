package com.example.soundbutton

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.soundbutton.extensions.setOnSoundClickListener

class MainActivity : AppCompatActivity() {
    private lateinit var btnSound : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSound = findViewById(R.id.btnSound)
        SoundPoolPlayer.setSoundId(R.raw.button_click).build(this)
        btnSound.setOnSoundClickListener {
            Log.d(TAG, "onCreate: ")
        }
    }

    override fun onDestroy() {
        SoundPoolPlayer.release()
        super.onDestroy()
    }
}