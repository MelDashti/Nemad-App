package com.example.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project.api.main.response.OrganizationalUnits
import com.example.project.databinding.OrganizationListItemBinding

class OrganizationItemAdapter(private val clickListener: OrganizationItemListener) :
    ListAdapter<OrganizationalUnits, OrganizationItemViewHolder>(OrganizationItemDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationItemViewHolder {
        return OrganizationItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrganizationItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}


class OrganizationItemDiffUtilCallback : DiffUtil.ItemCallback<OrganizationalUnits>() {
    override fun areItemsTheSame(
        oldItem: OrganizationalUnits,
        newItem: OrganizationalUnits
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: OrganizationalUnits,
        newItem: OrganizationalUnits
    ): Boolean {
        return oldItem == newItem
    }
}

class OrganizationItemViewHolder(val bind: OrganizationListItemBinding) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(organization: OrganizationalUnits, clickListener: OrganizationItemListener) {
        bind.clickListener = clickListener
        bind.organization = organization
    }

    companion object {
        fun from(parent: ViewGroup): OrganizationItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = OrganizationListItemBinding.inflate(inflater, parent, false)
            return OrganizationItemViewHolder(binding)
        }
    }
}

class OrganizationItemListener(val ClickListener: (organizationalUnit: OrganizationalUnits) -> Unit) {
    fun onClick(organizationalUnits: OrganizationalUnits) = ClickListener(organizationalUnits)
}