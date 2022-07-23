package com.example.internproject.ui.main

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.internproject.R
import com.example.internproject.adapter.CategoryItemAdapter
import com.example.internproject.adapter.CategoryItemListener
import com.example.internproject.databinding.MainFragmentBinding
import com.example.internproject.domain.Category
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {


    val viewModel: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false);
        binding.viewModel = viewModel
        binding.lifecycleOwner = this



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.statusBarColor = requireActivity().getColor(R.color.white)
        };
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {

            }
            true
        }

        val categoryListAdapter = CategoryItemAdapter(CategoryItemListener {
            //            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListFragment(it))
        })

        categoryListAdapter.submitList(initializeCategoryData())

        binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        addDecorationToGroupRecyclerView()
        binding.categoryRecyclerView.adapter = categoryListAdapter


        return binding.root
    }

    fun initializeCategoryData(): List<Category> {
        return listOf(
            Category(
                parentId = null,
                id = "1",
                title = "hello",
                description = null,
                children = null
            ),
            Category(
                id = "1",
                title = "hello",
                description = null,
                parentId = null,
                children = null
            ),
            Category(
                id = "1",
                title = "hello",
                description = null,
                parentId = null,
                children = null
            ),
            Category(
                id = "1",
                title = "hello",
                description = null,
                parentId = null,
                children = null
            ),
            Category(
                id = "1",
                title = "hello",
                description = null,
                parentId = null,
                children = null
            ),
            Category(
                id = "1",
                title = "hello",
                description = null,
                parentId = null,
                children = null
            )
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