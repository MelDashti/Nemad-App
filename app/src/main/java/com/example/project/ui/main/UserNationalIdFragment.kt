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
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.databinding.FragmentUserNationalIdBinding
import com.example.project.databinding.SettingsFragmentBinding
import com.google.android.material.snackbar.Snackbar

class NationalIdFragment : Fragment() {

    lateinit var binding: FragmentUserNationalIdBinding
    val viewModel: SettingsViewModel by navGraphViewModels(R.id.navigation3) { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserNationalIdBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.confirmButton.setOnClickListener {
            val nationalID = binding.nationalIdEditText.text.toString().trim()
            if (nationalID.length != 10) binding.nationalIdEditText.error =
                "کد ملی باید شامل ۱۰ کاراکتر عددی باشد"
            else {
                viewModel.setNationalId(nationalID)
            }
        }

        viewModel.userNationalIdResponse.observe(viewLifecycleOwner, {
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
                    viewModel.fetchSettings()
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