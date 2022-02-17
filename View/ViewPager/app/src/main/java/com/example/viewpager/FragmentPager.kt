package com.example.viewpager

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FragmentPager(var page : Int) : Fragment() {
    private lateinit var text : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ${page}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_pager, container, false)

        text = view.findViewById<TextView>(R.id.tvText)
        text.setText(page.toString())
        Log.d(TAG, "onCreateView: ${page}")
        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ${page}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ${page}")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ${page}")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ${page}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
}