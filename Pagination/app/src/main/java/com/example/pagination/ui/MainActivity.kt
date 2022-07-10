package com.example.pagination.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager2.widget.ViewPager2
import com.example.pagination.R
import com.example.pagination.ui.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var btnPrevPage: AppCompatButton
    private lateinit var btnNextPage: AppCompatButton
    private lateinit var vpViewPager: ViewPager2
    private val mAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(
            this,
            onItemClickListener = { id ->

            },
            onPrevClickListener = { position ->
                if (position > 0) vpViewPager.currentItem = position - 1
            },
            onNextClickListener = { position ->
                vpViewPager.currentItem = position + 1
            })
    }

    private val viewPagerCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (isTestingLoadMoreLocalData) {
                Log.d(
                    TAG,
                    "onPageSelected: Testing" + position + " " + (viewModel.chunkData.value!!.size - 1) + " " + viewModel.isCanLoadMoreData()
                )
            } else {
                Log.d(
                    TAG,
                    "onPageSelected: " + position + " " + (viewModel.characters.value!!.size - 1) + " " + viewModel.isCanLoadMoreData()
                )
            }
            if (position == mAdapter.itemCount-1 && viewModel.isCanLoadMoreData()) {
                viewModel.requestToLoadMoreData()
            }
        }
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPrevPage = findViewById(R.id.btnPrevPage)
        btnNextPage = findViewById(R.id.btnNextPage)
        vpViewPager = findViewById(R.id.vpViewPager)

        btnPrevPage.setOnClickListener {
            viewModel.requestToLoadPrevPage()
        }
        btnNextPage.setOnClickListener {
            viewModel.requestToLoadNextPage()
        }

        setUpViewPager()


        viewModel.characters.observe(this) {
            if (isTestingLoadMoreLocalData) {
                viewModel.initConditionToLocalLoadMore()
            } else {
                Log.d(TAG, "onCreate: " + it)
                mAdapter.submitList(it)
                mAdapter.notifyDataSetChanged()
            }
        }

        if (isTestingLoadMoreLocalData) {
            viewModel.chunkData.observe(this) {
                Log.d(TAG, "onCreate: chunkData " + it)
                mAdapter.submitList(it)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setUpViewPager() {
        vpViewPager.offscreenPageLimit = 3
        vpViewPager.adapter = mAdapter
        vpViewPager.registerOnPageChangeCallback(viewPagerCallBack)

    }

}