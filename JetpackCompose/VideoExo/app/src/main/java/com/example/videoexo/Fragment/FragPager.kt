package com.example.videoexo.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.videoexo.Fragment.PagerAdapter.VideoPagerAdapter
import com.example.videoexo.Mockup.MockupVideoData
import com.example.videoexo.Model.VideoModel
import com.example.videoexo.R
import com.example.videoexo.VideoViewModel


class FragPager : Fragment() {
//    private val viewModel by activityViewModels<VideoViewModel>()
    private lateinit var videoPagerAdapter: VideoPagerAdapter
    private lateinit var listVideo: List<VideoModel>
    private lateinit var view_pager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val viewRoot = inflater.inflate(R.layout.fragment_frag_pager,container,false)
        view_pager = viewRoot.findViewById(R.id.view_pager)
        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listVideo = MockupVideoData.videos
        videoPagerAdapter = VideoPagerAdapter(this, 3)
    }

    override fun onStart() {
        super.onStart()
        view_pager.adapter = videoPagerAdapter
//        viewModel.preLoadVideo(listVideo.map { it.mediaUrl })
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragPager()
    }
}