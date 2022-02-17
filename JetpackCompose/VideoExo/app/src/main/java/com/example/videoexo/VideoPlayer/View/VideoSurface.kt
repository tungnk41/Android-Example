package com.example.videoexo.VideoPlayer.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.videoexo.R

@Composable
fun VideoSurface(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    onPlayPauseClicked: (() -> Unit)? = null,
    onNextClicked: (()->Unit)? = null,
    onPreviousClicked: (()->Unit)? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.ic_previous), contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onPreviousClicked?.invoke()
                })
        Image(painter = painterResource(id = if(isPlaying) R.drawable.ic_pause else R.drawable.ic_play), contentDescription = "",
            modifier = Modifier
            .size(50.dp)
            .clickable {
                onPlayPauseClicked?.invoke()
            })
        Image(painter = painterResource(id = R.drawable.ic_next), contentDescription = "",
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onNextClicked?.invoke()
                })
    }
}