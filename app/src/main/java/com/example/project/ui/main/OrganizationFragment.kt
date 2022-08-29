package com.example.project.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.project.R
import com.example.project.adapter.OrganizationItemAdapter
import com.example.project.adapter.OrganizationItemListener
import com.example.project.databinding.OrganizationFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationFragment : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.navigation2) { defaultViewModelProviderFactory }
    lateinit var binding: OrganizationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OrganizationFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val organizationItemAdapter = OrganizationItemAdapter(OrganizationItemListener {
            // navigate to form fragment

            if (it.children.isNullOrEmpty()) {
                viewModel.isOrgLeafNode = true
                findNavController().navigate(R.id.action_organizationFragment_to_formFragment)
                viewModel.orgId = it.id
            } else {
                // display list
                viewModel.subOrgList(it)
            }

        })

        // search bar
        viewModel.startOrgSearch.observe(viewLifecycleOwner, {
            if (it) {
                initializeSearch()
                viewModel.searchOrgDone()
            }
        })


        viewModel.organizationalUnitsList.observe(viewLifecycleOwner, {
            organizationItemAdapter.submitList(it)
        })

        if (viewModel.isOrgLeafNode) {
            if (!viewModel.organizationalUnitsList.value.isNullOrEmpty())
                organizationItemAdapter.submitList(viewModel.organizationalUnitsList.value)
            binding.organizationRecyclerView.adapter = organizationItemAdapter
            viewModel.isOrgLeafNode = false
        }
        binding.organizationRecyclerView.adapter = organizationItemAdapter

        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider
            )!!
        )
        binding.organizationRecyclerView.addItemDecoration(itemDecorator)


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            handleBackButtonPressed()

        }

        binding.backButton.setOnClickListener {
            handleBackButtonPressed()
        }



        return binding.root
    }

    private fun handleBackButtonPressed() {
        if (viewModel.organizationalUnitsList.value.isNullOrEmpty()) {
            findNavController().popBackStack()

        }
        val parentNodeId = 1
        val currentParentId = viewModel.organizationalUnitsList.value?.get(0)?.parentId

        if (currentParentId == parentNodeId.toLong()) {
//            viewModel.clearOrgData()
            findNavController().popBackStack()
        } else {
            viewModel.setAsOrgParent()

        }
    }

    private fun initializeSearch() {
        val searchView = binding.searchBar
        binding.searchBar.isIconified = false
        viewModel.searchOrgDone()
        searching(searchView)
    }

    private fun searching(search: androidx.appcompat.widget.SearchView) {
        search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchOrgNow(newText)
                return false
            }
        })
    }


}