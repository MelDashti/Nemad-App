package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.adapter.RequestItemAdapter
import com.example.project.adapter.RequestItemListener
import com.example.project.databinding.FragmentRequestsBinding

class RequestsFragment : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph)
    lateinit var binding: FragmentRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestsBinding.inflate(inflater)
        binding.viewModel = viewModel
        // now use nav graph


        val adapter = RequestItemAdapter(RequestItemListener {

        })

        binding.requestRecyclerView.adapter = adapter
        viewModel.requestList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })






        return binding.root
    }

}