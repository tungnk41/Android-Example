package com.example.videoexo.ListVideo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.videoexo.Mockup.MockupVideoData
import com.example.videoexo.Model.VideoModel

class VideoViewModel :ViewModel(){
    val videos = MutableLiveData<List<VideoModel>>()
    val currentlyPlayingIndex = MutableLiveData<Int?>()

    init {
        videos.value = MockupVideoData.videos
    }

    fun onPlayVideoClick(playbackPosition: Long, videoIndex: Int) {
        when (currentlyPlayingIndex.value) {
            null -> currentlyPlayingIndex.postValue(videoIndex)
            videoIndex -> {
                currentlyPlayingIndex.postValue(null)
                videos.value = videos.value!!.toMutableList().also { list ->
                    list[videoIndex] = list[videoIndex].copy(lastPlayPosition = playbackPosition)
                }
            }
            else -> {
                videos.value = videos.value!!.toMutableList().also { list ->
                    list[currentlyPlayingIndex.value!!] = list[currentlyPlayingIndex.value!!].copy(lastPlayPosition = playbackPosition)
                }
                currentlyPlayingIndex.postValue(videoIndex)
            }
        }
    }

}