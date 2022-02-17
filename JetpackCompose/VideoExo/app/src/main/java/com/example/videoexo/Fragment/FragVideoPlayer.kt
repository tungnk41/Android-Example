package com.example.videoexo.Fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.videoexo.App
import com.example.videoexo.R
import com.example.videoexo.VideoViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource

private const val ARG_PARAM1 = "param1"

class FragVideoPlayer : Fragment() {

    private lateinit var videoUrl: String
    private lateinit var simplePlayer: SimpleExoPlayer
    private lateinit var cacheDataSourceFactory: CacheDataSource.Factory
    private lateinit var player_view: PlayerView

    companion object {
        @JvmStatic
        fun newInstance(videoUrl: String) =
            FragPager().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, videoUrl)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            videoUrl = it.getString(ARG_PARAM1).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val viewRoot = inflater.inflate(R.layout.fragment_frag_video_player, container, false)
//        player_view = viewRoot.findViewById(R.id.player_view)
        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
//        simplePlayer = SimpleExoPlayer.Builder(requireContext()).build()
//        cacheDataSourceFactory = CacheDataSource.Factory()
//            .setUpstreamDataSourceFactory(
//                DefaultDataSourceFactory(
//                    requireContext(),
//                    DefaultHttpDataSourceFactory()
//                )
//            )
//            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
//            .setCache(App.cache!!)
//        player_view.player = simplePlayer
//        prepare(videoUrl)
    }

    override fun onPause() {
//        pause()
        super.onPause()
    }

    override fun onResume() {
//        play()
        super.onResume()
    }

    override fun onDestroy() {
//        release()
        super.onDestroy()
    }

    private fun prepare(url: String) {
        val mediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
        simplePlayer.setMediaSource(mediaSource)
        simplePlayer.prepare()
        play()
    }

    private fun play() {
        simplePlayer.playWhenReady = true
    }

    private fun pause() {
        simplePlayer.playWhenReady = false
    }

    private fun release() {
        simplePlayer.stop(true)
        simplePlayer.release()
    }

}