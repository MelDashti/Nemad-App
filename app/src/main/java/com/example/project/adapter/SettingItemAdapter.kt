package com.example.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project.api.main.response.UserInfo
import com.example.project.databinding.SettingListItemBinding

class SettingItemAdapter(private val clickListener: SettingItemListener) :
    ListAdapter<UserInfo, SettingItemViewHolder>(SettingItemDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingItemViewHolder {
        return SettingItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SettingItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}


class SettingItemDiffUtilCallback : DiffUtil.ItemCallback<UserInfo>() {
    override fun areItemsTheSame(
        oldItem: UserInfo,
        newItem: UserInfo
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: UserInfo,
        newItem: UserInfo
    ): Boolean {
        return oldItem == newItem
    }
}

class SettingItemViewHolder(private val bind: SettingListItemBinding) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(userInfo: UserInfo, clickListener: SettingItemListener) {
        bind.clickListener = clickListener
        bind.userInfo = userInfo
    }

    companion object {
        fun from(parent: ViewGroup): SettingItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = SettingListItemBinding.inflate(inflater, parent, false)
            return SettingItemViewHolder(binding)
        }
    }
}

class SettingItemListener(val ClickListener: (userInfo: UserInfo) -> Unit) {
    fun onClick(userInfo: UserInfo) = ClickListener(userInfo)
}