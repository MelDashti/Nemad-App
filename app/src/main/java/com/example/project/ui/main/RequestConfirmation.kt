package com.example.project.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.project.BaseFragment
import com.example.project.R
import com.example.project.databinding.FragmentRequestConfirmationBinding
import com.example.project.util.setDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestConfirmation : BaseFragment() {

    override var bottomNavigationViewVisibility = View.VISIBLE
    val viewModel: MainViewModel by navGraphViewModels(R.id.navigation2) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentRequestConfirmationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestConfirmationBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        Log.d("fadsads", viewModel.complaintInfo.first.toString())
        Log.d("fadsads", viewModel.complaintInfo.second.toString())
        binding.uploadFileDescription.setDetails(viewModel.complaintInfo)




        return binding.root
    }

}