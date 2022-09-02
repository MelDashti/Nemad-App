package com.example.nemad.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.nemad.R
import com.example.nemad.databinding.VerificationFragmentBinding
import com.example.nemad.viewmodels.AuthSharedViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationFragment : Fragment() {

    val viewModel: AuthSharedViewModel by navGraphViewModels(R.id.navigation) { defaultViewModelProviderFactory }
    lateinit var binding: VerificationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VerificationFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.register.setOnClickListener {
            val code = binding.codeEditText.text.toString().trim()
            if (code.isNullOrEmpty()) {
                Snackbar.make(
                    binding.root,
                    "لطفاً کد ارسالی به تلفن همراه خود را وارد نمایید.",
                    Snackbar.LENGTH_LONG
                )
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.successful)
                    )
                    .show()


            } else {
                if (viewModel.verificationType == 0)
                    viewModel.verify(code)
                else viewModel.verifyResetPass(code)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


        viewModel.verifyResponse.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                if (it.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        "تایید شماره تلفن همراه با موفقیت انجام شد",
                        Snackbar.LENGTH_LONG
                    )
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(), R.color.successful)
                        ).show()

                    findNavController().navigate(R.id.action_verificationFragment_to_homeFragment)
                } else {
                    Snackbar.make(binding.root, "کد ورودی نامعتبر است.", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(), R.color.error)
                        ).show()
                }
            }
        })

        viewModel.verifyResetResponse.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                if (it.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Verification was successful, Please Enter New Password",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    findNavController().navigate(R.id.action_verificationFragment_to_resetFragment)
                } else {
                    Toast.makeText(requireContext(), "verification failed", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        })

        return binding.root
    }


}