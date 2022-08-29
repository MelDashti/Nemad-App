package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.databinding.FragmentUserPasswordBinding
import com.google.android.material.snackbar.Snackbar

class UserPasswordFragment : Fragment() {

    val viewModel: SettingsViewModel by navGraphViewModels(R.id.navigation3) { defaultViewModelProviderFactory }
    lateinit var binding: FragmentUserPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserPasswordBinding.inflate(inflater)
        binding.confirmButton.setOnClickListener {

            val currentPassword = binding.currentPasswordEditText.text.toString().trim()
            val newPassword = binding.passwordEditText.text.toString().trim()
            val repeatPassword = binding.repeatnePasswordEditText.text.toString().trim()
            when {
                currentPassword.isEmpty() -> {
                    binding.currentPasswordEditText.error = "این فیلد الزامی است"
                }
                newPassword.isEmpty() -> {
                    binding.passwordEditText.error = "این فیلد الزامی است"
                }

                newPassword.length < 6 || !newPassword.contains("[0-9]".toRegex()) -> {
                    binding.passwordEditText.error =
                        "کلم عبور باید شامل حداقل ۶ کاراکتر باشد و شامل حداقل یک حرف عددی باشد"
                }
                repeatPassword.isEmpty() -> {
                    binding.repeatnePasswordEditText.error = "این فیلد الزامی است"
                }
                repeatPassword != newPassword -> {
                    binding.repeatnePasswordEditText.error =
                        "تکرار کلمه عبور با کلمه عبور همخوانی ندارد."
                }
                else -> viewModel.setNewPassword(currentPassword, newPassword)

            }

        }

        viewModel.userPassResponse.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                if (it.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        "کلمه عبور با موفقیت تغییر یافت.",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(), R.color.successful)
                        )
                        .show()
                    viewModel.fetchSettings()
                    findNavController().popBackStack()
                } else {
                    Snackbar.make(
                        binding.root,
                        "اطلاعات ورودی نادرست است.",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(), R.color.error)
                        )
                        .show()
                }

            }
        })


// Inflate the layout for this fragment

        return binding.root
    }

}