package com.example.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project.api.main.response.Category
import com.example.project.api.main.response.MediaResponse
import com.example.project.databinding.CategoryListViewItemBinding
import com.example.project.databinding.MediaFileListItemBinding

class MediaFileAdapter() :
    ListAdapter<MediaResponse, MediaFileViewHolder>(MediaFileDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaFileViewHolder {
        return MediaFileViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MediaFileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class MediaFileDiffUtilCallback : DiffUtil.ItemCallback<MediaResponse>() {
    override fun areItemsTheSame(oldItem: MediaResponse, newItem: MediaResponse): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: MediaResponse, newItem: MediaResponse): Boolean {
        return oldItem == newItem
    }
}

class MediaFileViewHolder(val bind: MediaFileListItemBinding) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(mediaResponse: MediaResponse) {
        bind.media = mediaResponse
    }

    companion object {
        fun from(parent: ViewGroup): MediaFileViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MediaFileListItemBinding.inflate(inflater, parent, false)
            return MediaFileViewHolder(binding)
        }
    }
}