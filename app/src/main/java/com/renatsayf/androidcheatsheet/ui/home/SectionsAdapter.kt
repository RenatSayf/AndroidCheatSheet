package com.renatsayf.androidcheatsheet.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatsayf.androidcheatsheet.databinding.ItemSectionBinding

class SectionsAdapter : RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {

    private val list = listOf("Навигация", "View binding")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: ItemSectionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvHeader.text = item
        }
    }
}