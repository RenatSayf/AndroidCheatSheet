package com.renatsayf.androidcheatsheet.ui.sections.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatsayf.androidcheatsheet.databinding.ItemSimpleViewBinding
import com.renatsayf.androidcheatsheet.models.SimpleItem



//region Hint. RecyclerView implementation. Create the class of adapter
class SimpleAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() //Hint RecyclerView. Inherit your adapter from RecyclerView.Adapter<YourViewHolder>
{

    private val items = mutableListOf<SimpleItem>()

    //region Hint. RecyclerView. Override necessary methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSimpleViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
    //endregion

    //region Hint. RecyclerView. Function for items adding
    fun addItems(list: List<SimpleItem>) {
        items.addAll(list)
        notifyDataSetChanged()
    }
    //endregion

    //region Hint. RecyclerView. Create inner class ViewHolder, which inherently from RecyclerView.ViewHolder
    inner class ViewHolder(private val binding: ItemSimpleViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SimpleItem) {
            binding.tvHeader.text = item.header
            binding.tvContent.text = item.content

            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }
    //endregion

    //region Hint. RecyclerView. Interface for callback into fragment
    interface Listener {
        fun onItemClick(item: SimpleItem)
    }
    //endregion
}
//endregion