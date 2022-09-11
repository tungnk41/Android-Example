package com.example.recycle_in_recyleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recycle_in_recyleview.databinding.ItemRecycleChildBinding
import com.example.recycle_in_recyleview.model.ChildData

class ChildAdapter(
    val context: Context,
    val onItemClickListener: (Int) -> Unit
) : ListAdapter<ChildData, ChildAdapter.AdapterHolder>(ChildAdapterDiffUtil()) {
    class AdapterHolder(
        val binding: ItemRecycleChildBinding,
        val onItemClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener.invoke(adapterPosition)
            }
        }

        fun bind(data: ChildData) {
            binding.tvContent.text = data.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        return ChildAdapter.AdapterHolder(
            ItemRecycleChildBinding.inflate(LayoutInflater.from(context), parent, false),onItemClickListener)
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class ChildAdapterDiffUtil : DiffUtil.ItemCallback<ChildData>() {
    override fun areItemsTheSame(oldItem: ChildData, newItem: ChildData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ChildData, newItem: ChildData): Boolean {
        return oldItem.content.equals(newItem.content)
    }
}