package com.example.project.ui.auth

import android.graphics.Color.BLACK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.BaseFragment
import com.example.project.R
import com.example.project.databinding.FragmentLoginBinding
import com.example.project.ui.main.MainViewModel
import com.example.project.viewmodels.AuthSharedViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    override var bottomNavigationViewVisibility = View.GONE
    lateinit var binding: FragmentLoginBinding
    val viewModel: AuthSharedViewModel by navGraphViewModels(R.id.navigation) { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.loginButtonCLicked.observe(viewLifecycleOwner, {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            viewModel.login(username, password)
        })

        viewModel.response.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                Snackbar.make(binding.root, "ورود با موفقیت انجام شد", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.successful)
                    )
                    .show()
                findNavController().navigate(R.id.action_loginFragment2_to_homeFragment)
            } else
                Snackbar.make(binding.root, "ورود ناموفق بود", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.error)).show()
        })

        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_rememberPasswordFragment)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.registerQuestion.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_RegisterFragment)
        }

        return binding.root
    }

}