package com.example.nemad.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nemad.api.main.response.Category
import com.example.nemad.databinding.CategoryListViewItemBinding

class CategoryListItemAdapter(private val clickListener: CategoryListItemListener) :
    ListAdapter<Category, CategoryListItemViewHolder>(CategoryListItemDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListItemViewHolder {
        return CategoryListItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryListItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}


class CategoryListItemDiffUtilCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}

class CategoryListItemViewHolder(private val bind: CategoryListViewItemBinding) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(category: Category, clickListener: CategoryListItemListener) {
        bind.clickListener = clickListener
        bind.category = category
    }

    companion object {
        fun from(parent: ViewGroup): CategoryListItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CategoryListViewItemBinding.inflate(inflater, parent, false)
            return CategoryListItemViewHolder(binding)
        }
    }
}

class CategoryListItemListener(val ClickListener: (category: Category) -> Unit) {
    fun onClick(category: Category) = ClickListener(category)
}