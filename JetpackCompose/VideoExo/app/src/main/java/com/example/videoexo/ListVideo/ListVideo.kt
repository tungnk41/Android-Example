package com.example.videoexo.ListVideo

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.videoexo.Model.VideoModel
import com.google.android.exoplayer2.SimpleExoPlayer

@Composable
fun ListVideo(videos: List<VideoModel>, playingItemIndex: Int?, exoPlayer: SimpleExoPlayer, videoViewModel: VideoViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()){
        itemsIndexed(videos, { _, video -> video.id }) { index, video ->
            Spacer(modifier = Modifier.height(16.dp))
            VideoCard(
                videoModel = video,
                exoPlayer = exoPlayer,
                isPlayingIndex = playingItemIndex == index,
                onClick = {
                    videoViewModel.onPlayVideoClick(exoPlayer.currentPosition, index)
                }
            )
        }
    }
}