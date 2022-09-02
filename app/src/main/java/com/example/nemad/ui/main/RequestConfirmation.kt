package com.example.nemad.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.nemad.R
import com.example.nemad.databinding.FragmentRequestConfirmationBinding
import com.example.nemad.util.setDetails
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