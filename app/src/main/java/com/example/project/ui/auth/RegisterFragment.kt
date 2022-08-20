package com.example.project.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.BaseFragment
import com.example.project.R
import com.example.project.databinding.FragmentRegisterBinding
import com.example.project.ui.main.MainViewModel
import com.example.project.viewmodels.AuthSharedViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    override var bottomNavigationViewVisibility = View.GONE
    lateinit var binding: FragmentRegisterBinding
    val viewModel: AuthSharedViewModel by navGraphViewModels(R.id.navigation) { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.register.setOnClickListener {
            val username = binding.phoneEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            viewModel._userPass.postValue(Pair(username, password))
            viewModel.register(username, password)
        }

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {
                when {
                    it.code() == 428 -> {
                        findNavController().navigate(R.id.action_RegisterFragment_to_verificationFragment)
                        Snackbar.make(binding.root, "کد ارسال شد", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(
                                ContextCompat.getColor(requireContext(), R.color.successful)
                            )
                            .show()
                        viewModel.verificationType = 0
                    }
                    it.code() == 406 -> {

                        Snackbar.make(
                            binding.root,
                            "کاربری با این نام کاربری پیش از این ثبت شده است.",
                            Snackbar.LENGTH_LONG
                        )
                            .setBackgroundTint(
                                ContextCompat.getColor(requireContext(), R.color.error)
                            )
                            .show()
                    }
                    else -> {
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


            }
        })
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }


}