package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.project.adapter.RequestItemAdapter
import com.example.project.adapter.RequestItemListener
import com.example.project.adapter.SettingItemAdapter
import com.example.project.adapter.SettingItemListener
import com.example.project.databinding.SettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: SettingsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel


        val adapter = SettingItemAdapter(SettingItemListener {

        })
        adapter.submitList(listOf())



        return binding.root
    }


}