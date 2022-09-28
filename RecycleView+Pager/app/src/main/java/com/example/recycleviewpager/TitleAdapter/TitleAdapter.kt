package com.example.recycleviewpager.TitleAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleviewpager.R

class TitleAdapter(val onClickItem: (Int)->Unit) : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {

    var listData: List<String> = listOf("Tab 1", "Tab 2", "Tab 3")

    inner class TitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        fun bind(position: Int) {
            itemView.setOnClickListener {
                onClickItem.invoke(position)
            }
            tvTitle.text = listData[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_title,parent,false)
        return TitleViewHolder(view)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

}