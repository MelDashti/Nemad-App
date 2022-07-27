package com.example.project.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentRegisterBinding
import com.example.project.viewmodels.RegisterViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.action_RegisterFragment_to_verificationFragment)
                Toast.makeText(requireContext(), "کد ارسال شد", Toast.LENGTH_SHORT)
            } else {
                Toast.makeText(requireContext(), "خطا سرور", Toast.LENGTH_SHORT)
            }


        })



        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }



}