package com.example.project.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.project.BaseFragment
import com.example.project.R
import com.example.project.adapter.RequestItemAdapter
import com.example.project.adapter.RequestItemListener
import com.example.project.databinding.HomeFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {


    override var bottomNavigationViewVisibility = View.VISIBLE
    val viewModel: RequestsViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }
    lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater)
        binding.floatingButton.setOnClickListener {
            val navBar =
                requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
            navBar.menu.findItem(R.id.navigation2).isChecked = true
            val view: View = navBar.findViewById(R.id.navigation2)
            view.performClick()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.statusBarColor =
                requireActivity().getColor(R.color.primaryColor)
        }

        viewModel.refreshRecentRequests()

        val adapter = RequestItemAdapter(RequestItemListener {
            viewModel.requests.value = it
            findNavController().navigate(R.id.action_homeFragment_to_myRequestFragment)

        })
        binding.requestRecyclerView.adapter = adapter

        viewModel.recentRequestList.observe(viewLifecycleOwner, {
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