package com.example.viewpager

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(activity: AppCompatActivity, var size : Int) : FragmentStateAdapter(activity){

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentPager(0)
            1 -> fragment = FragmentPager(1)
            2 -> fragment = FragmentPager(2)
        }
        return fragment!!
    }




}