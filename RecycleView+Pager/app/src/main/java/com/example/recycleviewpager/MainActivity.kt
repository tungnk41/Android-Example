package com.example.recycleviewpager

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.recycleviewpager.TitleAdapter.TitleAdapter
import com.example.recycleviewpager.pager.PagerAdapter
import com.example.recycleviewpager.pager.PagerFragment

class MainActivity : AppCompatActivity() {

    lateinit var rvTitle: RecyclerView
    lateinit var vpPager: ViewPager2

    lateinit var titleAdapter : TitleAdapter
    lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvTitle = findViewById(R.id.rvTitle)
        vpPager = findViewById(R.id.vpPager)

        titleAdapter = TitleAdapter(onClickItem = {
            Log.d(TAG, "TitleAdapter Clicked: " + it)
            vpPager.currentItem = it
        })
        vpPager.registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d(TAG, "vpPager Selected: " + position)
            }
        })
        pagerAdapter = PagerAdapter(fragmentActivity = this, listOf(PagerFragment("123"),PagerFragment("456"),PagerFragment("789")))

        rvTitle.adapter = titleAdapter
        vpPager.adapter = pagerAdapter

    }
}