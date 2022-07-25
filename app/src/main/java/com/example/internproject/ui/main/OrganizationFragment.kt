package com.example.internproject.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.internproject.R
import com.example.internproject.adapter.OrganizationItemAdapter
import com.example.internproject.adapter.OrganizationItemListener
import com.example.internproject.api.main.response.Organization
import com.example.internproject.databinding.ActivityMainBinding.inflate
import com.example.internproject.databinding.OrganizationFragmentBinding
import com.example.internproject.databinding.VerificationFragmentBinding
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
            findNavController().navigate(R.id.action_organizationFragment_to_formFragment)
            viewModel.orgId = it
        })
        Log.d("list", "test123")
//        Log.d("list", viewModel.orgList!![0].title.toString())
        binding.organizationRecyclerView.adapter = organizationItemAdapter

        viewModel.orgList.observe(viewLifecycleOwner, Observer {
            organizationItemAdapter.submitList(it)
        })

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


}