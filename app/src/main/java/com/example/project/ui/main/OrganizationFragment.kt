package com.example.project.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.adapter.OrganizationItemAdapter
import com.example.project.adapter.OrganizationItemListener
import com.example.project.databinding.ActivityMainBinding.inflate
import com.example.project.databinding.OrganizationFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrganizationFragment : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }
    lateinit var binding: OrganizationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrganizationFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val organizationItemAdapter = OrganizationItemAdapter(OrganizationItemListener {
            // navigate to form fragment

            if (it.children.isNullOrEmpty()) {
                Log.d("hello", "empty")
                viewModel.isOrgLeafNode = true
                findNavController().navigate(R.id.action_organizationFragment_to_formFragment)
                viewModel.orgId = it.id
            } else {
                // display list
                Log.d("hello", "nope")
                viewModel.subOrgList(it)
            }

        })

        viewModel.organizationalUnitsList.observe(viewLifecycleOwner, Observer {
            organizationItemAdapter.submitList(it)
        })

        if (viewModel.isOrgLeafNode) {
            if (!viewModel.organizationalUnitsList.value.isNullOrEmpty())
                organizationItemAdapter.submitList(viewModel.organizationalUnitsList.value)
            binding.organizationRecyclerView.adapter = organizationItemAdapter
            viewModel.isOrgLeafNode = false
        }


        Log.d("list", "test123")
//        Log.d("list", viewModel.orgList!![0].title.toString())
        binding.organizationRecyclerView.adapter = organizationItemAdapter


        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
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
            Log.d("hahaa", "pop")
            findNavController().popBackStack()

        }
        val parentNodeId = 1
        val currentParentId = viewModel.organizationalUnitsList.value?.get(0)?.parentId

        if (currentParentId == parentNodeId.toLong()) {
            Log.d("hahaa", currentParentId.toString())
            Log.d("hahaa", "yoo")
//            viewModel.clearOrgData()
            findNavController().popBackStack()
        } else {
            Log.d("hahaa", "parentCategory")
            viewModel.setAsOrgParent()

        }
    }


}