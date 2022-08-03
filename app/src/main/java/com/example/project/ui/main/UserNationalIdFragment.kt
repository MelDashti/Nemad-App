package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.project.R
import com.example.project.databinding.FragmentUserNationalIdBinding
import com.example.project.databinding.SettingsFragmentBinding

class NationalIdFragment : Fragment() {

    lateinit var binding: FragmentUserNationalIdBinding
    val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserNationalIdBinding.inflate(inflater)

        binding.confirmButton.setOnClickListener {
            val nationalID = binding.nationalIdEditText.text.toString().trim()
            viewModel.setNationalId(nationalID)
        }


        return binding.root
    }

}