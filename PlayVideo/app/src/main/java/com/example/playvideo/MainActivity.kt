package com.example.playvideo


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource


class MainActivity : AppCompatActivity() {
    private lateinit var playerView : StyledPlayerView
    private lateinit var videoPlayer : ExoPlayer
    private lateinit var btnPlayPause : Button
    private lateinit var progressBar : ProgressBar
    private var isPlaying: MutableLiveData<Boolean> = MutableLiveData(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerView = findViewById(R.id.playerView)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        progressBar = findViewById(R.id.progressBar)
        videoPlayer = ExoPlayer.Builder(this).build()
        playerView.visibility = View.INVISIBLE
        prepare()

        btnPlayPause.setOnClickListener {
            isPlaying.value = !isPlaying.value!!
        }

        videoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when(playbackState) {
                    STATE_IDLE -> {
                        isPlaying.value = true
                    }
                    STATE_BUFFERING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    STATE_READY -> {
                        progressBar.visibility = View.GONE
                    }
                    STATE_ENDED -> {
                        isPlaying.value = false
                        videoPlayer.seekTo(0)
                        videoPlayer.playWhenReady = false
                        playerView.visibility = View.INVISIBLE
                    }
                }
            }
        })

        isPlaying.observe(this) { isPlaying ->
            setPlayButtonState(isPlaying)
            if (isPlaying) {
                play()
            } else {
                pause()
            }
        }
    }

    override fun onPause() {
        if (isPlaying.value == true) {
            videoPlayer.pause()
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (isPlaying.value == true) {
            videoPlayer.play()
        }
    }


    override fun onDestroy() {
        videoPlayer.stop()
        videoPlayer.release()
        super.onDestroy()
    }


    private fun prepare() {
        val uri = RawResourceDataSource.buildRawResourceUri(R.raw.test)
        val rawResourceDataSource = RawResourceDataSource(this)
        try {
            rawResourceDataSource.open(DataSpec(uri))
        }catch (e: Exception) {
            e.printStackTrace()
        }
        val factory: DataSource.Factory = DataSource.Factory { rawResourceDataSource }
        val mediaSource = ProgressiveMediaSource.Factory(factory)
            .createMediaSource(MediaItem.fromUri(uri))
        videoPlayer.setMediaSource(mediaSource)
        videoPlayer.prepare()
        videoPlayer.playWhenReady = false
        playerView.player = videoPlayer
    }

    private fun play() {
        playerView.visibility = View.VISIBLE
        videoPlayer.play()
    }

    private fun pause() {
        videoPlayer.pause()
    }

    private fun setPlayButtonState(isPlaying : Boolean) {
        if(isPlaying) {
            btnPlayPause.text = "Pause"
        }
        else {
            btnPlayPause.text = "Play"
        }
    }

}