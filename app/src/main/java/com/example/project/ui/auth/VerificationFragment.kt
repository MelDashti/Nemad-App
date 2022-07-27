package com.example.project.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
    ): View? {
        binding = VerificationFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
//        viewModel.onClickConfirm.observe(viewLifecycleOwner, Observer {
//            val code = binding.codeEditText.text.toString().trim()
//            viewModel.postCode(code)
//
//        })


        return binding.root
    }


}