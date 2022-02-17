package com.example.videoexo.VideoPagerPlayer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.videoexo.Mockup.MockupVideoData
import com.example.videoexo.VideoPlayer.View.VideoPlayer
import com.example.videoexo.VideoViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect


@ExperimentalPagerApi
@Composable
fun VideoPagerPlayer(videoViewModel: VideoViewModel = VideoViewModel()) {
    var pagerState = rememberPagerState(initialPage = 0)
    DisposableEffect(key1 = Unit, effect = {
        videoViewModel.preLoadVideo(MockupVideoData.videos.map { videoModel -> videoModel.mediaUrl }.toMutableList())
        onDispose {  }
    })
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->

        }

    }
    
    VerticalPager(state = pagerState, count = MockupVideoData.videos.size, modifier = Modifier.fillMaxSize()) { page ->
        VideoPlayer(page = page)
    }


}