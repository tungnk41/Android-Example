package com.example.pagination.ui.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagination.R
import com.example.pagination.api.model.Character
import kotlinx.android.synthetic.main.item_view_pager.view.*

class ViewPagerAdapter(
    private val context: Context,
    private val onItemClickListener: (Long) -> Unit,
    private val onPrevClickListener: (Int) -> Unit,
    private val onNextClickListener: (Int) -> Unit,
) : ListAdapter<Character, ViewPagerAdapter.ItemViewHolder>(CharacterDiffUtil()) {

    private val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    inner class ItemViewHolder(
        val contentView: View,
        private val onItemClickListener: (id: Long) -> Unit,
        private val onPrevClickListener: (position: Int) -> Unit,
        private val onNextClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(contentView) {
        fun bindData(data: Character) {
            val ivAvatar = contentView.findViewById<AppCompatImageView>(R.id.ivAvatar)
            val tvName = contentView.findViewById<AppCompatTextView>(R.id.tvName)
            val tvId = contentView.findViewById<AppCompatTextView>(R.id.tvId)
            val btnPrev = contentView.findViewById<AppCompatButton>(R.id.btnPrev)
            val btnNext = contentView.findViewById<AppCompatButton>(R.id.btnNext)
            Glide.with(context).load(data.image).into(ivAvatar)
            tvId.text = (data.id ?: -1).toString()
            tvName.text = data.name
            btnPrev.visibility = if(adapterPosition != 0) View.VISIBLE else View.INVISIBLE
            btnNext.visibility = if(adapterPosition < (data.totalItems-1)) View.VISIBLE else View.INVISIBLE

            contentView.setOnClickListener {
                onItemClickListener.invoke(data.id ?: -1)
            }
            btnPrev.setOnClickListener {
                onPrevClickListener.invoke(adapterPosition)
            }
            btnNext.setOnClickListener {
                onNextClickListener.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = layoutInflater.inflate(R.layout.item_view_pager, parent,false)
        return ItemViewHolder(view, onItemClickListener,onPrevClickListener,onNextClickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun submitList(list: List<Character>?) {
        val result = list?.map { it.copy() }
        super.submitList(result)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }

}



class CharacterDiffUtil : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}