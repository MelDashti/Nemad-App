package com.example.nemad.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.nemad.R
import com.example.nemad.databinding.ResetFragmentBinding
import com.example.nemad.viewmodels.AuthSharedViewModel
import com.google.android.material.snackbar.Snackbar

class ResetFragment : Fragment() {

    val viewModel: AuthSharedViewModel by navGraphViewModels(R.id.navigation) { defaultViewModelProviderFactory }
    lateinit var binding: ResetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResetFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.register.setOnClickListener {
            val password = binding.newPasswordEditText.text.toString().trim()
            viewModel.resetPassword(password)
        }

        viewModel.passwordChangedResponse.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                if (it.isSuccessful) {
                    Snackbar.make(binding.root, "رمز عبور شما با موفقیت تغییر یافت.", Snackbar.LENGTH_LONG)
                        .setBackgroundTint(
                            ContextCompat.getColor(requireContext(), R.color.successful)
                        )
                        .show()
                    findNavController().navigate(R.id.action_resetFragment_to_loginFragment2)
                }
            }
        })


        return binding.root
    }


}