package com.example.recycleviewpager.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recycleviewpager.R;

 class PagerFragment(private val title: String): Fragment() {

     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         val view = inflater.inflate(R.layout.fragment_pager,container,false)
         view.findViewById<TextView>(R.id.tvContentFragment).text = title
         return view
     }

}
