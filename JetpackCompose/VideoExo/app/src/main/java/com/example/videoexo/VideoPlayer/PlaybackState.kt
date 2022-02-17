package com.example.videoexo.VideoPlayer

import com.google.android.exoplayer2.Player

enum class PlaybackState(val value: Int) {
    IDLE(Player.STATE_IDLE),
    BUFFERING(Player.STATE_BUFFERING),
    READY(Player.STATE_READY),
    ENDED(Player.STATE_ENDED);

    companion object {
        fun get(value: Int): PlaybackState {
            return values().first { it.value == value }
        }
    }
}