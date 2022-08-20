package com.example.project.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.SettingsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: SettingsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        viewModel.settings.observe(viewLifecycleOwner, {
            binding.userName.text = it!!.firstName.toString() + " " + it!!.lastName.toString()
            binding.nationalId.text = it.nationalId.toString()
        })
        binding.signoutButton.setOnClickListener {
            showAlertDialog(it)
        }
        //signout section of the app
        binding.materialCardView.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_userNameFragment)
        }
        binding.materialCardView2.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_nationalIdFragment)
        }
        binding.materialCardView3.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_userPasswordFragment)
        }
        return binding.root
    }


    private fun showAlertDialog(it: View?) {
        val builder =
            AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        //titles
        builder.setTitle("خروج")
        builder.setMessage("آیا مطمئن هستید که می خواهید از سیستم خارج شوید؟")
        //buttons
        builder.setPositiveButton("تایید") { _, _ ->
            viewModel.signout()
            findNavController().navigate(R.id.action_settingsFragment_to_navigation)
        }
        builder.setNegativeButton("انصراف", null)
        builder.show()
    }


}