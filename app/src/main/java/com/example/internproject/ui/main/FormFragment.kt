package com.example.internproject.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.internproject.R
import com.example.internproject.databinding.FormFragmentBinding

class FormFragment : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }
    lateinit var binding: FormFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FormFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        Log.d("ID", viewModel.orgId.toString())
        Log.d("ID", viewModel.leafNodeCategoryId.toString())


        binding.sendRequest.setOnClickListener {
            val managerName = binding.managerNameTextInput.text.toString().trim()
            val complaintHeader = binding.complaintHeaderInputText.toString().trim()
            val complaintText = binding.complaintTextInput.toString().trim()

            viewModel.sendRequest(managerName, complaintHeader, complaintText)

        }









        return binding.root
    }


}