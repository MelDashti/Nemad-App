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
import com.example.project.databinding.VerificationFragmentBinding
import com.example.project.viewmodels.VerificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationFragment : Fragment() {


    val viewModel: VerificationViewModel by viewModels()
    lateinit var binding: VerificationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VerificationFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.register.setOnClickListener {
            val code = binding.codeEditText.text.toString().trim()
            if (code.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please Enter The Verification Code",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.verify(code)
            }
        }


        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful) {
            } else {

            }
        })



        return binding.root
    }


}