package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.project.databinding.FragmentRequestsBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.adapter.RequestItemListener
import dagger.hilt.android.AndroidEntryPoint
import com.example.project.adapter.RequestsPagingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RequestsFragment : Fragment() {

    val viewModel: RequestsViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }
    lateinit var binding: FragmentRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.floatingButton.setOnClickListener {
            findNavController().navigate(R.id.action_requestsFragment_to_mainFragment)
        }
        val pagedAdapter = RequestsPagingAdapter(RequestItemListener {
            viewModel.requests.value = it
            findNavController().navigate(R.id.action_requestsFragment_to_myRequestFragment)
        })
        binding.requestRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.requestRecyclerView.setHasFixedSize(true)
        binding.requestRecyclerView.adapter = pagedAdapter
        lifecycleScope.launch {
            viewModel.lolList.collectLatest { pagedData ->
                pagedAdapter.submitData(pagedData)
            }
        }


        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                com.example.project.R.drawable.divider
            )!!
        )
        binding.requestRecyclerView.addItemDecoration(itemDecorator)
        return binding.root
    }

}