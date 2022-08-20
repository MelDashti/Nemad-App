package com.example.project.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentUserNationalIdBinding
import com.example.project.databinding.SettingsFragmentBinding
import com.google.android.material.snackbar.Snackbar

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
            viewModel.fetchSettings()
        }

        viewModel.userNameResponse.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                if (it.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        "کد ملی با موفقیت تغییر یافت.",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(), R.color.successful)
                        )
                        .show()
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