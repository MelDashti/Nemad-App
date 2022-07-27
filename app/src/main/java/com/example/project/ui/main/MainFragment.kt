package com.example.project.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.R
import com.example.project.adapter.CategoryItemAdapter
import com.example.project.adapter.CategoryItemListener
import com.example.project.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false);
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        Log.d("test", viewModel.test.toString())
        viewModel.changeTest()
        Log.d("test", viewModel.test.toString())


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.statusBarColor = requireActivity().getColor(R.color.white)
        }

        val categoryListAdapter = CategoryItemAdapter(CategoryItemListener {
            //            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListFragment(it))
            if (it.children.isNullOrEmpty()) {
                Log.d("hello", "empty")
                viewModel.isLeafNode = true
                Log.d("lolwa", viewModel.isLeafNode.toString())
                findNavController().navigate(R.id.action_mainFragment_to_organizationFragment)
                viewModel.leafNodeCategoryId = it.id
            } else {
                // display list
                Log.d("hello", "nope")
                viewModel.subList(it)
            }
        })

        binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        addDecorationToGroupRecyclerView()


        //floating action button
        binding.floatingButton.setOnClickListener {

            if (!viewModel.categoryList.value.isNullOrEmpty()) {
                categoryListAdapter.submitList(viewModel.categoryList.value)
            } else {
                viewModel.fetchCat()
                viewModel.fetchOrg()
            }
            binding.categoryRecyclerView.adapter = categoryListAdapter
        }

        viewModel.categoryList.observe(viewLifecycleOwner, Observer {
            categoryListAdapter.submitList(it)
        })


        binding.bottomNavigation.setupWithNavController(findNavController())

        //set nav destinations for bottom navigation buttons
        binding.bottomNavigation.setOnItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, findNavController()) || onOptionsItemSelected(
                it
            )
        }



        Log.d("lolwa", viewModel.isLeafNode.toString())
        if (viewModel.isLeafNode) {
            Log.d("lolwa", "heeeel")
            binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            addDecorationToGroupRecyclerView()
            if (!viewModel.categoryList.value.isNullOrEmpty())
                categoryListAdapter.submitList(viewModel.categoryList.value)
            binding.categoryRecyclerView.adapter = categoryListAdapter
            viewModel.isLeafNode = false
        }


        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            if (viewModel.categoryList.value.isNullOrEmpty()) {
                Log.d("hahaa", "pop")
                findNavController().popBackStack()

            }
            val parentNodeId = 1
            val currentParentId = viewModel.categoryList.value?.get(0)?.parentId

            if (currentParentId == parentNodeId.toLong()) {
                Log.d("hahaa", "cool")
                viewModel.clearData()

            } else {
                Log.d("hahaa", "parentCategory")
                viewModel.setAsParent()
//                viewModel.getCategory(currentParentId!!)
            }


        }

        bottomNavigationSettings()

        return binding.root
    }


    private fun bottomNavigationSettings() {
        binding.bottomNavigation.selectedItemId = R.id.mainFragment
    }

    private fun addDecorationToGroupRecyclerView() {
        val dividerItemDecoration = DividerItemDecoration(
            context,
            DividerItemDecoration.HORIZONTAL
        )
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!
        )

        binding.categoryRecyclerView.addItemDecoration(dividerItemDecoration)
    }


}