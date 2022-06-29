package com.renatsayf.androidcheatsheet.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatsayf.androidcheatsheet.databinding.ItemSectionBinding
import com.renatsayf.androidcheatsheet.models.SectionHeader

class SectionsAdapter(
    private val headers: List<SectionHeader>,
    private val listener: Listener) : RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        holder.bind(headers[position])
    }

    override fun getItemCount(): Int {
        return headers.size
    }

    inner class ViewHolder(private val binding: ItemSectionBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SectionHeader) {
            binding.tvHeader.text = item.header

            binding.root.setOnClickListener {
                listener.sectionItemClick(item)
            }
        }
    }

    interface Listener {
        fun sectionItemClick(item: SectionHeader)
    }
}