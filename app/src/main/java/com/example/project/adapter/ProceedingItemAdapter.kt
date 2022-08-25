package com.example.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project.api.main.response.MediaResponse
import com.example.project.api.main.response.OrganizationalUnits
import com.example.project.api.main.response.Proceeding
import com.example.project.databinding.OrganizationListItemBinding
import com.example.project.databinding.ProceedingsListItemBinding

class ProceedingItemAdapter :
    ListAdapter<Proceeding, ProceedingItemViewHolder>(ProceedingItemDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProceedingItemViewHolder {
        return ProceedingItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProceedingItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class ProceedingItemDiffUtilCallback : DiffUtil.ItemCallback<Proceeding>() {
    override fun areItemsTheSame(
        oldItem: Proceeding,
        newItem: Proceeding
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: Proceeding,
        newItem: Proceeding
    ): Boolean {
        return oldItem == newItem
    }
}

class ProceedingItemViewHolder(private val bind: ProceedingsListItemBinding) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(proceeding: Proceeding) {
        bind.proceeding = proceeding
        val adapterFiles = MediaFileAdapter()
        bind.uploadedFileRecyclerVIew.adapter = adapterFiles
        var list = mutableListOf<MediaResponse>()

        proceeding.attachments?.forEach {
            list.add(MediaResponse(title = it.title, url = it.url))
        }

        adapterFiles.submitList(list)

    }

    companion object {
        fun from(parent: ViewGroup): ProceedingItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProceedingsListItemBinding.inflate(inflater, parent, false)
            return ProceedingItemViewHolder(binding)
        }
    }
}
