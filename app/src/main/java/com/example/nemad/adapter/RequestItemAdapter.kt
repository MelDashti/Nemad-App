package com.example.nemad.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nemad.api.main.response.Requests
import com.example.nemad.databinding.RequestListItemBinding

class RequestItemAdapter(private val clickListener: RequestItemListener) :
    ListAdapter<Requests, RequestItemViewHolder>(RequestItemDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestItemViewHolder {
        return RequestItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RequestItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}


class RequestItemDiffUtilCallback : DiffUtil.ItemCallback<Requests>() {
    override fun areItemsTheSame(
        oldItem: Requests,
        newItem: Requests
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Requests,
        newItem: Requests
    ): Boolean {
        return oldItem === newItem
    }
}

class RequestItemViewHolder(private val bind: RequestListItemBinding) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(requests: Requests, clickListener: RequestItemListener) {
        bind.clickListener = clickListener
        bind.request = requests
    }

    companion object {
        fun from(parent: ViewGroup): RequestItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RequestListItemBinding.inflate(inflater, parent, false)
            return RequestItemViewHolder(binding)
        }
    }
}

class RequestItemListener(val ClickListener: (request: Requests) -> Unit) {
    fun onClick(request: Requests) = ClickListener(request)
}