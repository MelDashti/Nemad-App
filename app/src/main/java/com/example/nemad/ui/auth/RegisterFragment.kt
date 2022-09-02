package com.example.nemad.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.nemad.BaseFragment
import com.example.nemad.R
import com.example.nemad.databinding.FragmentRegisterBinding
import com.example.nemad.viewmodels.AuthSharedViewModel
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
            val repeatPassword = binding.repeatPasswordEditText.text.toString().trim()
            when {
                username.length != 11 -> {
                    binding.phoneEditText.error =
                        "شماره تلفن همراه باید شامل ۱۱ رقم باشد و با ۰۹ شروع شود"
                }
                username.substring(0, 2) != "09" -> {
                    binding.phoneEditText.error =
                        "شماره تلفن همراه باید شامل ۱۱ رقم باشد و با ۰۹ شروع شود"
                }
                username.isEmpty() -> {
                    binding.phoneEditText.error = "این فیلد الزامی است"
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = "این فیلد الزامی است"
                }
                repeatPassword.isEmpty() -> {
                    binding.repeatPasswordEditText.error = "این فیلد الزامی است"
                }
                password.length < 6 || !password.contains("[0-9]".toRegex()) -> {
                    binding.passwordEditText.error =
                        "کلم عبور باید شامل حداقل ۶ کاراکتر باشد و شامل حداقل یک حرف عددی باشد"
                }
                repeatPassword != password -> {
                    binding.repeatPasswordEditText.error =
                        "تکرار کلمه عبور با کلمه عبور همخوانی ندارد."
                }
                else -> {
                    viewModel._userPass.postValue(Pair(username, password))
                    viewModel.register(username, password)
                }
            }

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