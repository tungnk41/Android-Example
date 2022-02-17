package com.example.videoexo.ListVideo

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

@Composable
fun VideoScreen(viewModel: VideoViewModel = VideoViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context).build().apply {
            addListener(object : Player.EventListener {
                //            override fun onPlaybackStateChanged(state: Int) {
//                super.onPlaybackStateChanged(state)
//                Log.d("TAG", "onPlayerStateChanged: " + playbackState)
//            }

                //  @IntDef({STATE_IDLE, STATE_BUFFERING, STATE_READY, STATE_ENDED})
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                   // isPlaying = playWhenReady
                }
//            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
//                super.onPlayWhenReadyChanged(playWhenReady, reason)
//                Log.d("TAG", "onPlayWhenReadyChanged: " + playWhenReady)
//                isPlaying = playWhenReady
//            }
            })
        }
    }
    val videos by viewModel.videos.observeAsState(listOf())
    val playingItemIndex by viewModel.currentlyPlayingIndex.observeAsState()
    val isCurrentItemVisible = remember { mutableStateOf(false) }


    LaunchedEffect(playingItemIndex) {
        if (playingItemIndex == null) {
            exoPlayer.pause()
        } else {
            val video = videos[playingItemIndex!!]
            exoPlayer.setMediaItem(MediaItem.fromUri(video.mediaUrl), video.lastPlayPosition)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    LaunchedEffect(isCurrentItemVisible.value) {
        if (!isCurrentItemVisible.value && playingItemIndex != null) {
            viewModel.onPlayVideoClick(exoPlayer.currentPosition, playingItemIndex!!)
        }
    }
    DisposableEffect(exoPlayer) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> exoPlayer.pause()
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }


    ListVideo(
        videos = videos,
        playingItemIndex = playingItemIndex,
        exoPlayer = exoPlayer,
        videoViewModel = viewModel
    )

}