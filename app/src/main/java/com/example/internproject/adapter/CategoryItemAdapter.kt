package com.example.internproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.internproject.api.main.response.Category
import com.example.internproject.databinding.CategoryListItemBinding

class CategoryItemAdapter(private val clickListener: CategoryItemListener) :
    ListAdapter<Category, CategoryItemViewHolder>(CategoryItemDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}


class CategoryItemDiffUtilCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}

class CategoryItemViewHolder(val bind: CategoryListItemBinding) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(category: Category, clickListener: CategoryItemListener) {
        bind.clickListener = clickListener
        bind.category = category
    }

    companion object {
        fun from(parent: ViewGroup): CategoryItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CategoryListItemBinding.inflate(inflater, parent, false)
            return CategoryItemViewHolder(binding)
        }
    }
}

class CategoryItemListener(val ClickListener: (category: Category) -> Unit) {
    fun onClick(category: Category) = ClickListener(category)
}