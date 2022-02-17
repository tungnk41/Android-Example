package com.example.videoexo.ListVideo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.videoexo.Model.VideoModel
import com.example.videoexo.R
import com.google.android.exoplayer2.SimpleExoPlayer

@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    videoModel: VideoModel,
    isPlayingIndex: Boolean,
    exoPlayer: SimpleExoPlayer,
    onClick: () -> Unit
) {
    var isSurfaceUiVisible by remember { mutableStateOf(false) }
    val isControlButtonVisible = isSurfaceUiVisible || !isPlayingIndex

    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (isPlayingIndex) {
            VideoPlayer(exoPlayer) { uiVisible ->
                isSurfaceUiVisible = uiVisible
            }
        } else {
            VideoThumbnail(videoModel.thumbnail)
        }
        if (isControlButtonVisible) {
            Icon(
                painter = painterResource(if (isPlayingIndex) R.drawable.ic_pause else R.drawable.ic_play),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clickable {
                        onClick()
                    })
        }
    }
}