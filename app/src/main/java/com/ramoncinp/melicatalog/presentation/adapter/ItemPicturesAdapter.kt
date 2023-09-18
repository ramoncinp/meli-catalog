package com.ramoncinp.melicatalog.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramoncinp.melicatalog.data.models.ItemPicture
import com.ramoncinp.melicatalog.databinding.PictureItemLayoutBinding

class ItemPictureAdapter :
    ListAdapter<ItemPicture, ItemPictureAdapter.ViewHolder>(ItemPictureDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(
        private val binding: PictureItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemPicture) {
            binding.pictureImageView.setItemImage(item.secureURL)
        }

        private fun ImageView.setItemImage(url: String) {
            Glide.with(this.context).load(url).into(this)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PictureItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ItemPictureDiffCallback : DiffUtil.ItemCallback<ItemPicture>() {
    override fun areItemsTheSame(oldItem: ItemPicture, newItem: ItemPicture): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemPicture, newItem: ItemPicture): Boolean {
        return oldItem == newItem
    }
}
