package com.example.project.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.SettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: SettingsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        viewModel.settings.observe(viewLifecycleOwner, {
            binding.userName.text = it!!.firstName.toString() + " " + it!!.lastName.toString()
            binding.nationalId.text = it.nationalId.toString()
        })

        binding.signoutButton.setOnClickListener {
            viewModel.signout()
            findNavController().navigate(R.id.action_settingsFragment_to_navigation)
        }
        //signout section of the app

        binding.materialCardView.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_userNameFragment)
        }

        binding.materialCardView2.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_nationalIdFragment)
        }

        binding.materialCardView3.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_userPasswordFragment)
        }

        return binding.root
    }


}