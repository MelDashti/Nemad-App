package com.example.project.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.databinding.ResetFragmentBinding
import com.example.project.viewmodels.AuthSharedViewModel

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
                    Toast.makeText(requireContext(), "Password Changed", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_resetFragment_to_loginFragment2)
                }
            }
        })


        return binding.root
    }


}