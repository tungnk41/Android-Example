package com.example.loadmore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class InfinityScrollAdapter(private val context: Context, private val itemList : List<String?>) : ListAdapter<String?,RecyclerView.ViewHolder>(RvDiffUtil()) {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType === VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.item_list,parent, false)
            ItemViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            showItemDataList((holder as ItemViewHolder?)!!, position)
        } else if (holder is LoadingViewHolder) {
//            showLoadingView((holder as LoadingViewHolder?)!!, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItem: TextView = itemView.findViewById(R.id.tvItem)
    }

    private class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

    }

    private class RvDiffUtil : DiffUtil.ItemCallback<String?>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.contentEquals(newItem)
        }
    }

    private fun showLoadingView(viewHolder: LoadingViewHolder, position: Int) {
        //ProgressBar would be displayed
    }

    private fun showItemDataList(viewHolder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        viewHolder.tvItem.text = item
    }
}