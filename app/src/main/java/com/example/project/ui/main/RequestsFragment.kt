package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.project.adapter.RequestItemAdapter
import com.example.project.databinding.FragmentRequestsBinding
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.project.R


class RequestsFragment : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }

    //    val viewModel: MainViewModel by viewModels()
//        val viewModel: MainViewModel by navGraphViewModels(com.example.project.R.id.nav_graph)
    lateinit var binding: FragmentRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestsBinding.inflate(inflater)
        binding.viewModel = viewModel
        // now use nav graph


        val adapter = RequestItemAdapter()
        (binding.requestRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false

        binding.requestRecyclerView.adapter = adapter
        viewModel.requestList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

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