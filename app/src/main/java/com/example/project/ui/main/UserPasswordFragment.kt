package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.databinding.FragmentUserPasswordBinding

class UserPasswordFragment : Fragment() {

    val viewModel: SettingsViewModel by navGraphViewModels(R.id.navigation3) { defaultViewModelProviderFactory }
    lateinit var binding: FragmentUserPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserPasswordBinding.inflate(inflater)
        binding.confirmButton.setOnClickListener {
            val currentPassword = binding.currentPasswordEditText.text.toString().trim()
            val newPassword = binding.passwordEditText.text.toString().trim()
            viewModel.setNewPassword(currentPassword, newPassword)
            findNavController().popBackStack()
            viewModel.fetchSettings()
        }

        // Inflate the layout for this fragment

        return binding.root
    }

}