package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentUserNameBinding
import com.google.android.material.snackbar.Snackbar

class UserNameFragment : Fragment() {

    lateinit var binding: FragmentUserNameBinding
    val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserNameBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.nameEditText.setText(viewModel.settings.value?.firstName)
        binding.surnameEditText.setText(viewModel.settings.value?.lastName)

        binding.confirmButton.setOnClickListener {
            val firstName = binding.nameEditText.text.toString().trim()
            val lastName = binding.surnameEditText.text.toString().trim()
            viewModel.setUserName(firstName, lastName)
            viewModel.fetchSettings()
        }

        viewModel.userNameResponse.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {

                if (it.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        "نام و نام خانوادگی با موفقیت تغییر یافت.",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(), R.color.successful)
                        )
                        .show()
                    val firstName = binding.nameEditText.text.toString().trim()
                    val lastName = binding.surnameEditText.text.toString().trim()
                    viewModel.updateUserName(firstName, lastName)
                    findNavController().popBackStack()
                } else {
                    Snackbar.make(
                        binding.root,
                        "خطا سرور",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(), R.color.error)
                        )
                        .show()
                }
            }
        })


        return binding.root
    }

}