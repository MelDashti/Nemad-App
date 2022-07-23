package com.example.internproject.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.internproject.databinding.FragmentRegisterBinding
import com.example.internproject.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    val viewModel: RegisterViewModel by viewModels();


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.onClickRegister.observe(viewLifecycleOwner, Observer {
            val username = binding.phoneEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            viewModel.register(username, password)
        })



        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}