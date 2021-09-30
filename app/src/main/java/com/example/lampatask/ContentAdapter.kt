package com.example.lampatask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lampatask.databinding.ItemContentBinding

class ContentAdapter: RecyclerView.Adapter<ContentAdapter.ItemViewHolder>() {

    private val items: ArrayList<Content> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ItemViewHolder(val binding: ItemContentBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.title.text = "Test title"
            binding.link.text = "Test link"
            binding.time.text = "- test time post"
        }
    }


    fun setItems(data: List<Content>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

}