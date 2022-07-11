package com.example.loadmore

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

fun RecyclerView.initLoadMore(offset: Int = 1 ,onRequestToLoadMoreData: (() -> Unit)? = null) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            adapter?.let {
                if( !(offset >=1 && offset <= it.itemCount -1 )) throw Throwable("RecyclerView.initLoadMore : Offset need to inRange 1..Total-1")
                try {
                    if( (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == it.itemCount - offset) {
                        onRequestToLoadMoreData?.invoke()
                    }
                }
                catch (e :Exception) {
                    e.printStackTrace()
                }
            }
        }
    })
}