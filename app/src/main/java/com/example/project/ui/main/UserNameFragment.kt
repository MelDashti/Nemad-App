package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentUserNameBinding

class UserNameFragment : Fragment() {

    lateinit var binding: FragmentUserNameBinding
    val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserNameBinding.inflate(inflater)
        binding.confirmButton.setOnClickListener {
            val firstName = binding.nameEditText.text.toString().trim()
            val lastName = binding.surnameEditText.text.toString().trim()
            viewModel.setUserName(firstName, lastName)
            findNavController().popBackStack()
            viewModel.fetchSettings()
        }


        return binding.root
    }

}