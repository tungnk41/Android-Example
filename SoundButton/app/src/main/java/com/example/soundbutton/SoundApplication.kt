package com.example.soundbutton

import android.app.Application


class SoundApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initSoundPool()
    }

    private fun initSoundPool() {
        SoundPoolPlayer.setSoundId(R.raw.button_click).build(this)
    }

}