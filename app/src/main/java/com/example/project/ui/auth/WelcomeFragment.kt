package com.example.project.ui.auth

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.example.project.BaseFragment
import com.example.project.R
import com.example.project.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override var bottomNavigationViewVisibility = View.GONE


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_RegisterFragment)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.statusBarColor =
                requireActivity().getColor(R.color.welcome_status_bar)
        }

        binding.signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_loginFragment2)
        }


        return binding.root
    }


}