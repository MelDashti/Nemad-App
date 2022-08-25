package com.example.project.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.databinding.RememberPasswordFragmentBinding
import com.example.project.ui.main.MainViewModel
import com.example.project.viewmodels.AuthSharedViewModel
import com.google.android.material.snackbar.Snackbar

class RememberPasswordFragment : Fragment() {


    val viewModel: AuthSharedViewModel by navGraphViewModels(R.id.navigation) { defaultViewModelProviderFactory }
    lateinit var binding: RememberPasswordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RememberPasswordFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.register.setOnClickListener {
            val phoneNo = binding.mobileNumberEditText.text.toString().trim()
            viewModel.sendPhoneNumber(phoneNo)
        }

        viewModel.rememberPass.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
            if (it.code() == 428) {
                findNavController().navigate(R.id.action_rememberPasswordFragment_to_verificationFragment)
                viewModel.verificationType = 1
                Snackbar.make(binding.root, "کد ارسال شد", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.successful)).show()
            } else if (it.code() == 406) {
                Toast.makeText(
                    requireContext(),
                    "",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("code", "406")
            } else {
                Toast.makeText(requireContext(), "خطا سرور", Toast.LENGTH_SHORT).show()
            }

        }})


        return binding.root
    }

}