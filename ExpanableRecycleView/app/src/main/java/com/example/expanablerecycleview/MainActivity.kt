package com.example.expanablerecycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var rvListData: RecyclerView
    lateinit var adapter: ExpandableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvListData = findViewById(R.id.rvListData)
        adapter = ExpandableAdapter {

        }
        rvListData.adapter = adapter


    }
}