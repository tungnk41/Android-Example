package com.example.playvideo


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource


class MainActivity : AppCompatActivity() {
    private lateinit var playerView : StyledPlayerView
    private lateinit var videoPlayer : ExoPlayer
    private lateinit var btnOpen : Button
    private lateinit var progressBar : ProgressBar
    private var mediaSourceIntro : MediaSource? = null
    private var mediaSourceOpen : MediaSource? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        playerView = findViewById(R.id.playerView)
        btnOpen = findViewById(R.id.btnOpen)
        progressBar = findViewById(R.id.progressBar)
        videoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = videoPlayer
        mediaSourceIntro = prepareIntro()
        mediaSourceOpen = prepareOpen()
        setFullScreen()
        initState()
        btnOpen.setOnClickListener {
            mediaSourceOpen?.let {
                playOpen(it)
            }
        }

        videoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when(playbackState) {
                    STATE_IDLE -> {

                    }
                    STATE_BUFFERING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    STATE_READY -> {
                        progressBar.visibility = View.GONE
                    }
                    STATE_ENDED -> {
                        videoPlayer.seekTo(0)
                        videoPlayer.playWhenReady = false
                        playerView.alpha = 0f
                        initState()
                    }
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onDestroy() {
        videoPlayer.stop()
        videoPlayer.release()
        super.onDestroy()
    }

    private fun prepareIntro() : MediaSource {
        val uri = RawResourceDataSource.buildRawResourceUri(R.raw.intro)
        val rawResourceDataSource = RawResourceDataSource(this)
        try {
            rawResourceDataSource.open(DataSpec(uri))
        }catch (e: Exception) {
            e.printStackTrace()
        }
        val factory: DataSource.Factory = DataSource.Factory { rawResourceDataSource }
       return ProgressiveMediaSource.Factory(factory)
            .createMediaSource(MediaItem.fromUri(uri))

    }

    private fun prepareOpen() : MediaSource {
        val uri = RawResourceDataSource.buildRawResourceUri(R.raw.test)
        val rawResourceDataSource = RawResourceDataSource(this)
        try {
            rawResourceDataSource.open(DataSpec(uri))
        }catch (e: Exception) {
            e.printStackTrace()
        }
        val factory: DataSource.Factory = DataSource.Factory { rawResourceDataSource }
        return ProgressiveMediaSource.Factory(factory)
            .createMediaSource(MediaItem.fromUri(uri))

    }

    private fun playIntro(mediaSource : MediaSource) {
        videoPlayer.setMediaSource(mediaSource)
        videoPlayer.repeatMode = REPEAT_MODE_ONE
        videoPlayer.prepare()
        videoPlayer.playWhenReady = true
    }

    private fun playOpen(mediaSource : MediaSource) {
        videoPlayer.setMediaSource(mediaSource)
        videoPlayer.repeatMode = REPEAT_MODE_OFF
        videoPlayer.prepare()
        videoPlayer.playWhenReady = true
    }

    private fun setFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun removeFullScreen() {
        window.clearFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun initState() {
        playerView.alpha = 1f
        mediaSourceIntro?.let {
            playIntro(it)
        }
    }

}