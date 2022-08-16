package com.example.project.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.api.main.MainApiService
import com.example.project.api.main.response.Requests
import com.example.project.databinding.RequestListItemBinding

class RequestsPagingAdapter(private val clickListener: RequestItemListener) :
    PagingDataAdapter<Requests, RequestsPagingAdapter.PassengersViewHolder>(PassengersComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PassengersViewHolder {
        return PassengersViewHolder(
            RequestListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: PassengersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindPassenger(it, clickListener) }
    }

    inner class PassengersViewHolder(private val binding: RequestListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindPassenger(requests: Requests, clickListener2: RequestItemListener) =
            with(binding) {
                request = requests
                clickListener = clickListener2
                itemTitle.text = "hazards"
                when (requests.statusStr) {
                    "WaitingForAcceptance" -> {
                        toggle.setImageResource(R.drawable.waitingforacceptance)
                        statusTitle.text = "در انتزار تایید"
                    }
                    "WaitingForConfirmation" -> {
                        toggle.setImageResource(R.drawable.waitingforconfirmation)
                        statusTitle.text = "بررسی شده"
                    }

                    "UnderOrganizationalInspection" -> {
                        toggle.setImageResource(R.drawable.underorganizatoininspection)
                        statusTitle.text = "در حال بررسی"
                    }
                    "Done" -> {
                        toggle.setImageResource(R.drawable.done)
                        statusTitle.text = "انجام شده"
                    }
                }

            }
    }

    object PassengersComparator : DiffUtil.ItemCallback<Requests>() {
        override fun areItemsTheSame(oldItem: Requests, newItem: Requests): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Requests, newItem: Requests): Boolean {
            return oldItem == newItem
        }
    }
}



class PassengersDataSource(
    private val api: MainApiService
) : PagingSource<Int, Requests>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Requests> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = api.getRequestData(nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < 604) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
