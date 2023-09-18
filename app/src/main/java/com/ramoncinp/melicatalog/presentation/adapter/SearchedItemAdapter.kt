package com.ramoncinp.melicatalog.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramoncinp.melicatalog.data.models.SearchedItem
import com.ramoncinp.melicatalog.databinding.SearchedItemBinding
import com.ramoncinp.melicatalog.domain.utils.formatAsCurrency

class SearchedItemAdapter :
    ListAdapter<SearchedItem, SearchedItemAdapter.ViewHolder>(SearchedItemDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(
        private val binding: SearchedItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchedItem) {
            with(binding) {
                title.text = item.title
                itemPrice.text = item.price.formatAsCurrency()
                currency.text = item.currencyId
                itemImage.setItemImage(item.thumbnail)
            }
        }

        private fun ImageView.setItemImage(url: String) {
            Glide.with(this.context).load(url).into(this)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchedItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class SearchedItemDiffCallback : DiffUtil.ItemCallback<SearchedItem>() {
    override fun areItemsTheSame(oldItem: SearchedItem, newItem: SearchedItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchedItem, newItem: SearchedItem): Boolean {
        return oldItem == newItem
    }
}
