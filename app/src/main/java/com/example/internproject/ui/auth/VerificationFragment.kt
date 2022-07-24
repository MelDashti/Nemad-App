package com.example.internproject.ui.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.internproject.R
import com.example.internproject.databinding.VerificationFragmentBinding
import com.example.internproject.viewmodels.RegisterViewModel
import com.example.internproject.viewmodels.VerificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationFragment : Fragment() {


    val viewModel: RegisterViewModel by viewModels()
    lateinit var binding: VerificationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VerificationFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.onClickConfirm.observe(viewLifecycleOwner, Observer {
            val code = binding.codeEditText.text.toString().trim()
            viewModel.postCode(code)

        })


        return binding.root
    }


}