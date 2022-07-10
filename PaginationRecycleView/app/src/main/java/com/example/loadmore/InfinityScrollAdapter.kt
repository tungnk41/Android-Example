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


class InfinityScrollAdapter(private val context: Context) : ListAdapter<String?,RecyclerView.ViewHolder>(RvDiffUtil()) {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    private inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItem: TextView = itemView.findViewById(R.id.tvItem)
        fun bind(position: Int) {
            tvItem.text = getItem(position)
        }
    }

    private inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        fun bind() {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

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
            holder.bind(position)
        } else if (holder is LoadingViewHolder) {
            holder.bind()
        }
    }
}

private class RvDiffUtil : DiffUtil.ItemCallback<String?>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.contentEquals(newItem)
    }
}