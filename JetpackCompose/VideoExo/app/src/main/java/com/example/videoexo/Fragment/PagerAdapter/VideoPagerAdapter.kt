package com.example.videoexo.Fragment.PagerAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videoexo.Fragment.FragVideoPlayer
import com.example.videoexo.Model.VideoModel

class VideoPagerAdapter(fragment: Fragment, var size: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return FragVideoPlayer.newInstance("")
    }
}