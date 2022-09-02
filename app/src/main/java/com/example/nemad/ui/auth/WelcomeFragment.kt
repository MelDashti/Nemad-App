package com.example.nemad.ui.auth

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.nemad.BaseFragment
import com.example.nemad.R
import com.example.nemad.databinding.FragmentWelcomeBinding
import com.example.nemad.viewmodels.AuthSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment() {

    lateinit var binding: FragmentWelcomeBinding
    override var bottomNavigationViewVisibility = View.GONE
    val viewModel: AuthSharedViewModel by navGraphViewModels(R.id.navigation) { defaultViewModelProviderFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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