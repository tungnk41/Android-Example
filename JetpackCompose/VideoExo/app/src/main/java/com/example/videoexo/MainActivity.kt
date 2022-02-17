package com.example.videoexo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentTransaction
import com.example.videoexo.App.Companion.context
import com.example.videoexo.Fragment.FragPager
import com.example.videoexo.VideoPagerPlayer.VideoPagerPlayer
import com.example.videoexo.ui.theme.VideoExoTheme
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {
    private val videoViewModel by viewModels<VideoViewModel>()

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_main)


        setContent {
            VideoExoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    VideoScreen()
//                        VideoPlayer()
                    VideoPagerPlayer()
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
    }
    override fun onDestroy() {
        super.onDestroy()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}
