package com.example.project.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.project.BaseFragment
import com.example.project.R
import com.example.project.databinding.FragmentLoginBinding
import com.example.project.viewmodels.LoginViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    override var bottomNavigationViewVisibility = View.GONE
    lateinit var binding: FragmentLoginBinding
    val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loginButtonCLicked.observe(viewLifecycleOwner, Observer {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val flag = viewModel.login(username, password)
        })

        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "ورود با موفقیت", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment2_to_WelcomeFragment)
            } else (Toast.makeText(requireContext(), "ورود ناموفق بود", Toast.LENGTH_SHORT)).show()
        })

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)

        view.visibility = View.GONE
    }


}