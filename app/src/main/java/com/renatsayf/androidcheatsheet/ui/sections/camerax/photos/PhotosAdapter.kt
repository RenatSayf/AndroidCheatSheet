package com.renatsayf.androidcheatsheet.ui.sections.camerax.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.renatsayf.androidcheatsheet.databinding.PhotoItemBinding
import com.renatsayf.androidcheatsheet.models.PhotoModel

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private val photoList = mutableListOf<PhotoModel>()

    fun addItems(list: List<PhotoModel>) {
        photoList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    inner class ViewHolder(private val binding: PhotoItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(photoModel: PhotoModel) {

            with(binding) {

                //region Hint Coil_image_loading_from_file
                imgView.load(photoModel.file)
                //endregion
            }
        }
    }

}