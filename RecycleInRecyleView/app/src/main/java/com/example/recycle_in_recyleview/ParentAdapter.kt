package com.example.recycle_in_recyleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recycle_in_recyleview.databinding.ItemRecycleParentBinding
import com.example.recycle_in_recyleview.model.ParentData

class ParentAdapter(
    val context: Context,
    val onItemClickListener: (Int,Int) -> Unit): ListAdapter<ParentData, ParentAdapter.AdapterHolder>(ParentAdapterDiffUtil())
{
    class AdapterHolder(
        val context: Context,
        val binding: ItemRecycleParentBinding,
        val onItemClickListener: (Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val childAdapter = ChildAdapter(
            context = context,
            onItemClickListener = {
            onItemClickListener.invoke(adapterPosition,it)
        })

        init {
            binding.rvItemParent.adapter = childAdapter
            binding.rvItemParent.isNestedScrollingEnabled = false
        }

        fun bind(data : ParentData) {
            binding.tvTitle.text = data.title
            childAdapter.submitList(data.childData)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        return AdapterHolder(context,ItemRecycleParentBinding.inflate(LayoutInflater.from(context),parent,false), onItemClickListener = onItemClickListener)
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class ParentAdapterDiffUtil: DiffUtil.ItemCallback<ParentData>() {
    override fun areItemsTheSame(oldItem: ParentData, newItem: ParentData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ParentData, newItem: ParentData): Boolean {
        return oldItem.title.equals(newItem.title)
    }
}