package com.example.videoexo.VideoPlayer.View

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.videoexo.Mockup.MockupVideoData
import com.example.videoexo.Model.VideoModel
import com.example.videoexo.VideoPlayer.Controller.VideoController
import com.example.videoexo.VideoPlayer.VideoPlayback
import com.example.videoexo.VideoPlayer.VideoPlayerState


@Composable
fun VideoPlayer(page: Int) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val videoController = remember {VideoController(context)}
    var isShowSurfaceUI by remember { mutableStateOf(false)}
    val isPlaying by videoController.isPlaying.observeAsState(true)
    SideEffect {
        Log.d(TAG, "VideoPlayer: " + page.toString())
    }
    DisposableEffect(page, lifecycleOwner) {
        videoController.setSource(MockupVideoData.videos[page])
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                videoController.setVideoCurrentIndex(page)
                Log.d(TAG, "onCreate: " + page)
            }
            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                Log.d(TAG, "onStart: ")
            }
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                videoController.prepare()
                Log.d(TAG, "onResume: ")
            }
            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                Log.d(TAG, "onPause: ")
            }
            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                Log.d(TAG, "onStop: ")
            }
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                Log.d(TAG, "onDestroy: ")
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)) {
        VideoPlayback( modifier = Modifier.fillMaxSize(),
            onPlayerViewAvailable = {
            videoController.setPlayerView(it)
        },
        onControllerVisibilityChanged = {
            isShowSurfaceUI = it
        })
        if(isShowSurfaceUI){
            VideoSurface(
                isPlaying = isPlaying,
                modifier = Modifier.fillMaxSize(),
                onPlayPauseClicked = {
                    videoController.playPauseToggle()
                },
                onPreviousClicked = {
                    videoController.onPrevious()
                },
                onNextClicked = {
                    videoController.onNext()
                }
            )
        }
    }

}