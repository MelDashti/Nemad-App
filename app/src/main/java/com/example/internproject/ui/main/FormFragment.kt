package com.example.internproject.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.internproject.R
import com.example.internproject.databinding.FormFragmentBinding
import com.example.internproject.viewmodels.LoginViewModel

class FormFragment : Fragment() {


    val viewModel: FormViewModel by viewModels()
    lateinit var binding: FormFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FormFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


}