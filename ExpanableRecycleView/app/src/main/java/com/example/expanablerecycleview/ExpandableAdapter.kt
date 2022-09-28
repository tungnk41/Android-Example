package com.example.expanablerecycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ExpandableAdapter(val onClickItem: (Int)->Unit) : RecyclerView.Adapter<ExpandableAdapter.AdapterViewHolder>() {

    var listData: List<Item> = listOf(Item(title="Tab 1", contentExpand = "Content Expandable"), Item(title="Tab 2", contentExpand = "Content Expandable"), Item(title="Tab 3", contentExpand = "Content Expandable"))

    inner class AdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvExpanded = itemView.findViewById<TextView>(R.id.tvExpanded)
        fun bind(position: Int) {
            itemView.setOnClickListener {
                listData[position].isExpanded = !listData[position].isExpanded
                if (listData[position].isExpanded){
                    tvExpanded.visibility = View.VISIBLE
                }  else {
                    tvExpanded.visibility = View.GONE
                }
            }
            tvTitle.text = listData[position].title
            tvExpanded.text = listData[position].contentExpand
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

}

data class Item(val title: String, val contentExpand: String, var isExpanded: Boolean = false)
