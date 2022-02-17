package com.example.videoexo.VideoPlayer.Controller

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.videoexo.App
import com.example.videoexo.Mockup.MockupVideoData
import com.example.videoexo.Model.VideoModel
import com.example.videoexo.VideoPlayer.PlaybackState
import com.example.videoexo.VideoPlayer.VideoPlayerState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class VideoController(
    val context: Context,
)  {
    private  var listSource: List<VideoModel>? = null
    private  lateinit var source: VideoModel
    private var playerView: PlayerView? = null
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSource: MediaSource

    var currentVideoIndex = 0
        private set

    var isPlaying = MutableLiveData<Boolean>(false)
        private set


    init {
        initExoPlayer()
    }

    fun initExoPlayer() {
        exoPlayer = SimpleExoPlayer.Builder(context)
            .build()
            .apply {
                playWhenReady = isPlaying
                addListener(object : Player.EventListener {
                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                        if (PlaybackState.get(playbackState) == PlaybackState.READY) {

                        }
                    }
                })
            }
    }

    fun setListSource(listVideo : List<VideoModel>){
        this.listSource = listVideo
        currentVideoIndex = 0
        source = listVideo[currentVideoIndex]
        prepare()
    }

     fun setSource(videoModel: VideoModel) {
        this.source = videoModel
        prepare()
    }

     fun setPlayerView(playerView: PlayerView) {
        this.playerView = playerView
        playerView.player = exoPlayer
    }

     fun play() {
        exoPlayer.playWhenReady = true
        isPlaying.value = true
        if (exoPlayer.playbackState == Player.STATE_ENDED) {
            exoPlayer.seekTo(0)
        }
    }

     fun pause() {
        exoPlayer.playWhenReady = false
        isPlaying.value = false
    }

     fun playPauseToggle() {
        if (exoPlayer.isPlaying) pause() else play()
    }

     fun onPrevious() {
        currentVideoIndex--
         listSource?.let {
             if(currentVideoIndex < 0) currentVideoIndex  = it.size - 1
         }
    }

     fun onNext() {
        currentVideoIndex++
         listSource?.let {
             if(currentVideoIndex >= it.size) currentVideoIndex = 0
         }
    }

     fun quickSeekForward() {

    }

     fun quickSeekRewind() {

    }

     fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

     fun stop() {
        exoPlayer.stop()
    }

     fun prepare(){
         setMediaSource(source.mediaUrl)
         exoPlayer.prepare()
    }

     fun release() {
        exoPlayer.release()
    }

    fun setVideoCurrentIndex(index: Int){
        listSource?.let {
            if(index < it.size) currentVideoIndex = 0
            else if(index >= it.size) currentVideoIndex = it.size-1
            else currentVideoIndex = index

            source = it[currentVideoIndex]
        }
    }

    private fun setMediaSource(url : String){
        val extension: String = url.substring(url.lastIndexOf("."))

        val urlType = when(extension){
            ".mp4" -> URLType.MP4
            ".m3u8" ->URLType.HLS
            else -> -1
        }

        when(urlType){
            URLType.MP4 -> {
                 val httpDataSourceFactory = DefaultHttpDataSourceFactory()
                 val dataSourceFactory = DefaultDataSourceFactory(context, httpDataSourceFactory)
                 var cacheDataSourceFactory: CacheDataSource.Factory = CacheDataSource.Factory()
                    .setUpstreamDataSourceFactory(dataSourceFactory)
                    .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
                     .setCache(App.cache!!)
                mediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(
                    MediaItem.fromUri(Uri.parse(url)))
            }
            URLType.HLS->{
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context, DefaultHttpDataSourceFactory())
                mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(Uri.parse(url)))
            }
        }
        exoPlayer.setMediaSource(mediaSource)
    }

    enum class URLType{
        HLS,MP4
    }
}

