package com.example.internproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.internproject.api.main.response.Organization
import com.example.internproject.databinding.OrganizationListItemBinding

class OrganizationItemAdapter(private val clickListener: OrganizationItemListener) :
    ListAdapter<Organization, OrganizationItemViewHolder>(OrganizationItemDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizationItemViewHolder {
        return OrganizationItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrganizationItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}


class OrganizationItemDiffUtilCallback : DiffUtil.ItemCallback<Organization>() {
    override fun areItemsTheSame(oldItem: Organization, newItem: Organization): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Organization, newItem: Organization): Boolean {
        return oldItem == newItem
    }
}

class OrganizationItemViewHolder(val bind: OrganizationListItemBinding) :
    RecyclerView.ViewHolder(bind.root) {
    fun bind(organization: Organization, clickListener: OrganizationItemListener) {
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

class OrganizationItemListener(val ClickListener: (organizationId: Long) -> Unit) {
    fun onClick(organization: Organization) = ClickListener(organization.id)
}