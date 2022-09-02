package com.example.nemad.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nemad.BaseFragment
import com.example.nemad.R
import com.example.nemad.adapter.CategoryItemAdapter
import com.example.nemad.adapter.CategoryItemListener
import com.example.nemad.adapter.CategoryListItemAdapter
import com.example.nemad.adapter.CategoryListItemListener
import com.example.nemad.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : BaseFragment() {

    override var bottomNavigationViewVisibility = View.VISIBLE
    val viewModel: MainViewModel by navGraphViewModels(R.id.navigation2) { defaultViewModelProviderFactory }

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val categoryListAdapter = CategoryItemAdapter(CategoryItemListener {
            if (it.children.isNullOrEmpty()) {
                viewModel.isLeafNode = true
                findNavController().navigate(R.id.action_mainFragment_to_organizationFragment)
                viewModel.leafNodeCategoryId = it.id

            } else {
                // display list
                viewModel.subList(it)
            }
        })

        val categoryListViewAdapter = CategoryListItemAdapter(CategoryListItemListener {
            if (it.children.isNullOrEmpty()) {
                viewModel.isLeafNode = true
                findNavController().navigate(R.id.action_mainFragment_to_organizationFragment)
                viewModel.leafNodeCategoryId = it.id
            } else {
                // display list
                binding.searchBar.setQuery("", false)
                viewModel.subList(it)
            }

        })


        if (viewModel.checkIfRootNode()) {
            binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            addDecorationToGroupRecyclerView()
            binding.categoryRecyclerView.adapter = categoryListAdapter
        } else {
            binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            addDecorationToListViewRecyclerView()
            binding.categoryRecyclerView.adapter = categoryListViewAdapter
        }

        binding.searchBar.setOnClickListener {
            binding.searchBar.isIconified = false
            viewModel.searchDone()
            initializeSearch()
        }

        viewModel.categoryList.observe(viewLifecycleOwner, {

            if (viewModel.checkIfRootNode() && !viewModel.searchEnabled) {

                binding.categoryRecyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 2)
                addDecorationToGroupRecyclerView()
                binding.categoryRecyclerView.adapter = categoryListAdapter
                categoryListAdapter.submitList(it)

            } else {
                binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                addDecorationToListViewRecyclerView()
                binding.categoryRecyclerView.adapter = categoryListViewAdapter
                categoryListViewAdapter.submitList(it)
            }
        })


        if (viewModel.isLeafNode) {
            if (viewModel.checkIfRootNode()) {
                binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                addDecorationToGroupRecyclerView()
                if (!viewModel.categoryList.value.isNullOrEmpty())
                    categoryListAdapter.submitList(viewModel.categoryList.value)
                binding.categoryRecyclerView.adapter = categoryListAdapter
            } else {
                binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                addDecorationToListViewRecyclerView()
                if (!viewModel.categoryList.value.isNullOrEmpty())
                    categoryListViewAdapter.submitList(viewModel.categoryList.value)
                binding.categoryRecyclerView.adapter = categoryListViewAdapter
            }

            viewModel.isLeafNode = false
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            if (viewModel.categoryList.value.isNullOrEmpty()) {
                findNavController().popBackStack()
            }

            val parentNodeId = 1
            val currentParentId = viewModel.categoryList.value?.get(0)?.parentId
            if (currentParentId == parentNodeId.toLong()) {
                viewModel.clearData()
                findNavController().popBackStack()
            } else {
                viewModel.setAsParent()
            }
        }
        return binding.root
    }

    private fun initializeSearch() {
        val searchView = binding.searchBar
        searching(searchView)
    }

    private fun searching(search: androidx.appcompat.widget.SearchView) {
        search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchNow(newText)

                return false
            }
        })
    }


    private fun addDecorationToGroupRecyclerView() {
        val dividerItemDecoration = DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!
        )

        while (binding.categoryRecyclerView.itemDecorationCount > 0) {
            binding.categoryRecyclerView.removeItemDecorationAt(0)
        }
        binding.categoryRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun addDecorationToListViewRecyclerView() {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider
            )!!
        )
        while (binding.categoryRecyclerView.itemDecorationCount > 0) {
            binding.categoryRecyclerView.removeItemDecorationAt(0)
        }
        binding.categoryRecyclerView.addItemDecoration(itemDecorator)
    }

}