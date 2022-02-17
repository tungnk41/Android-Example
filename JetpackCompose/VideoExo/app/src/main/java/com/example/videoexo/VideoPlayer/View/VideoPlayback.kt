package com.example.videoexo.VideoPlayer

import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.videoexo.R
import com.google.android.exoplayer2.ui.PlayerView


@Composable
fun VideoPlayback(modifier: Modifier = Modifier, onPlayerViewAvailable: (PlayerView) -> Unit, onControllerVisibilityChanged: ((Boolean)->Unit)? = null) {

    val context = LocalContext.current
    val playerViewInflate = remember {
        val layout = LayoutInflater.from(context).inflate(R.layout.video_player, null, false)
        val playerViewInflate = layout.findViewById(R.id.playerView) as PlayerView
        playerViewInflate.apply {
            setControllerVisibilityListener {
                onControllerVisibilityChanged?.invoke(it == View.VISIBLE)
            }
            setControllerShowTimeoutMs(5000)
            onPlayerViewAvailable(this)
        }
    }

    AndroidView(
        factory = { context ->
//            PlayerView(context).apply {
//                useController = false
//                onPlayerViewAvailable(this)
//            }
            playerViewInflate

        },
        modifier = modifier
    )
}