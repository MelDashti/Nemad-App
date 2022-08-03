package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.project.R
import com.example.project.databinding.FragmentUserPasswordBinding

class UserPasswordFragment : Fragment() {

    lateinit var binding: FragmentUserPasswordBinding
    val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserPasswordBinding.inflate(inflater)
        binding.confirmButton.setOnClickListener {
            val currentPassword = binding.currentPasswordEditText.text.toString().trim()
            val newPassword = binding.passwordEditText.text.toString().trim()
            viewModel.setNewPassword(currentPassword,newPassword)
        }

        // Inflate the layout for this fragment

        return binding.root
    }

}