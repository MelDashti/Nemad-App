package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.adapter.ProceedingItemAdapter
import com.example.project.databinding.FragmentMyRequestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRequestFragment : Fragment() {

    val viewModel: RequestsViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }
    lateinit var binding: FragmentMyRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyRequestBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val adapter = ProceedingItemAdapter()
        binding.proceedingsRecyclerView.adapter = adapter

        if (viewModel.requests.value?.proceedings?.isEmpty() == true){
            binding.proceedingsRecyclerView.visibility=View.INVISIBLE
        }

        viewModel.requests.observe(viewLifecycleOwner, {
            adapter.submitList(it.proceedings)
        })


        return binding.root
    }
}