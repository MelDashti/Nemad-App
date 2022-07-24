package com.example.internproject.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internproject.R
import com.example.internproject.adapter.CategoryItemAdapter
import com.example.internproject.adapter.CategoryItemListener
import com.example.internproject.adapter.OrganizationItemAdapter
import com.example.internproject.adapter.OrganizationItemListener
import com.example.internproject.api.main.response.Category
import com.example.internproject.databinding.MainFragmentBinding
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
        };
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {

            }
            true
        }

        //organization related code
//        val organizationItemAdapter = OrganizationItemAdapter(OrganizationItemListener {
//            // navigate to form fragment
//            findNavController().navigate(R.id.action_mainFragment_to_formFragment)
//        })


        var subList: List<Category>? = null

        val categoryListAdapter = CategoryItemAdapter(CategoryItemListener {
            //            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListFragment(it))
            if (it.children.isNullOrEmpty()) {
                Log.d("hello", "empty")
                findNavController().navigate(R.id.action_mainFragment_to_organizationFragment)
                // move to organizations
//                if (!viewModel.orgList.isNullOrEmpty()) {

                //                    organizationItemAdapter.submitList(viewModel.orgList)
//                    binding.categoryRecyclerView.adapter = organizationItemAdapter
            } else {
                // display list
                Log.d("hello", "nope")
                subList = viewModel.subList(it)
                viewModel.showSublist()
            }
        })

        viewModel.showSublist.observe(viewLifecycleOwner, Observer {
            categoryListAdapter.submitList(subList)
        })


//        categoryListAdapter.submitList(initializeCategoryData())


        //floating action button
        binding.floatingButton.setOnClickListener {
            binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            addDecorationToGroupRecyclerView()
            val listOfCategory: List<Category>? = null
            if (!viewModel.list.isNullOrEmpty())
                categoryListAdapter.submitList(viewModel.list)
            binding.categoryRecyclerView.adapter = categoryListAdapter


        }








        return binding.root
    }

    fun initializeCategoryData(): List<Category> {
        return listOf(
            Category()
//            Category(
//                parentId = null,
//                id = "1",
//                title = "hello",
//                description = null,
//                children = null
//            ),
//            Category(
//                id = "1",
//                title = "hello",
//                description = null,
//                parentId = null,
//                children = null
//            ),
//            Category(
//                id = 1,
//                title = "hello",
//                description = null,
//                parentId = null,
//                children = null
//            ),
//            Category(
//                id = 1,
//                title = "hello",
//                description = null,
//                parentId = null,
//                children = null
//            ),
//            Category(
//                id = 1,
//                title = "hello",
//                description = null,
//                parentId = null,
//                children = null
//            ),
//            Category(
//                id = 1,
//                title = "hello",
//                description = null,
//                parentId = null,
//                children = null
//            )
        )
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