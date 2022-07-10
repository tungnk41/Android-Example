package com.example.loadmore

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvList: RecyclerView
    private lateinit var mAdapter: InfinityScrollAdapter
    private var dataList = mutableListOf<String?>()
    private var isLoading = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter = InfinityScrollAdapter(this,dataList)
        rvList = findViewById(R.id.rvList)
        rvList.adapter = mAdapter
        initData()
        initInfinityScrollAdapter()

    }

    private fun initInfinityScrollAdapter() {
        rvList.addOnScrollListener( object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = rvList.layoutManager as GridLayoutManager
                if(!isLoading) {
                    if(layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == dataList.size - 1) {
                        isLoading = true
                        loadMore()
                    }
                }
            }
        })
    }

    private fun loadMore() {
        dataList.add(null)
        mAdapter.notifyItemInserted(dataList.size - 1)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            dataList.removeAt(dataList.size - 1)
            val listSize: Int = dataList.size
            mAdapter.notifyItemRemoved(listSize)
            var currentSize = listSize
            val nextSize = currentSize + 10
            while (currentSize - 1 < nextSize) {
                dataList.add("Item $currentSize")
                currentSize++
            }
            mAdapter.notifyDataSetChanged()
            isLoading = false
        }, 2000)
    }

    private fun initData() {
        var i = 0
        while (i < 10) {
            dataList.add("Item $i")
            i++
        }
    }
}