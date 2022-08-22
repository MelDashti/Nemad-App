package com.example.project.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.BaseFragment
import com.example.project.R
import com.example.project.databinding.FragmentRequestConfirmationBinding
import com.example.project.util.setDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestConfirmation : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.navigation2) { defaultViewModelProviderFactory }
    private lateinit var binding: FragmentRequestConfirmationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequestConfirmationBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.uploadFileDescription.setDetails(viewModel.complaintInfo)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            findNavController().popBackStack(R.id.homeFragment, false)
        }

        binding.sendRequest.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }


        return binding.root
    }

}