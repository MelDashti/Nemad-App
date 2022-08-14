package com.example.project.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.databinding.VerificationFragmentBinding
import com.example.project.ui.main.MainViewModel
import com.example.project.viewmodels.AuthSharedViewModel
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
                Toast.makeText(
                    requireContext(),
                    "Please Enter The Verification Code",
                    Toast.LENGTH_SHORT
                ).show()
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
            Log.d("verification","na")

            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "verification was successful", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_verificationFragment_to_loginFragment2)
            } else {
                Toast.makeText(requireContext(), "verification failed", Toast.LENGTH_SHORT)
                    .show()


            }
        })

        viewModel.verifyResetResponse.observe(viewLifecycleOwner, Observer {
            Log.d("verification","na")

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
        })




        return binding.root
    }


}