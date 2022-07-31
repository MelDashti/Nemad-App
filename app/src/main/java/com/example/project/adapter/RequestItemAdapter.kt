package com.example.project.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.api.main.response.Requests
import com.example.project.databinding.RequestListItemBinding

class RequestItemAdapter() :
    ListAdapter<Requests, RequestItemViewHolder>(RequestItemDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestItemViewHolder {
        return RequestItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RequestItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class RequestItemDiffUtilCallback : DiffUtil.ItemCallback<Requests>() {
    override fun areItemsTheSame(
        oldItem: Requests,
        newItem: Requests
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: Requests,
        newItem: Requests
    ): Boolean {
        return oldItem == newItem
    }
}

class RequestItemViewHolder(val bind: RequestListItemBinding) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(requests: Requests) {
        bind.request = requests
        var expanded = requests.expanded
        bind.toggle.setOnClickListener {
            expanded = !expanded
            bind.subItem.visibility = if (expanded) View.VISIBLE else View.GONE
            if (expanded) {
                bind.toggle.setImageResource(R.drawable.invertedarrow)
            } else {
                bind.toggle.setImageResource(R.drawable.dropdown)
            }

            requests.expanded = expanded
        }
    }

    companion object {
        fun from(parent: ViewGroup): RequestItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RequestListItemBinding.inflate(inflater, parent, false)
            return RequestItemViewHolder(binding)
        }
    }
}









