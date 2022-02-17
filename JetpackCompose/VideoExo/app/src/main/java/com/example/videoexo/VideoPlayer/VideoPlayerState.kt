package com.example.videoexo.VideoPlayer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoPlayerState(
    var isPlaying: Boolean = false,
    var controlsVisible: Boolean = true,
    var controlsEnabled: Boolean = true,
    var gesturesEnabled: Boolean = true,
    var duration: Long = 1L,
    var currentPosition: Long = 1L,
    var secondaryProgress: Long = 1L,
    val videoSize: Pair<Float, Float> = 1920f to 1080f,
    var playbackState: PlaybackState = PlaybackState.IDLE,
): Parcelable