package com.example.project.ui.auth

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.project.R
import com.example.project.databinding.FragmentWelcomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class WelcomeFragment : Fragment() {


    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        hideBottomNavBar()

        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_RegisterFragment)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().window.statusBarColor =
                requireActivity().getColor(R.color.welcome_status_bar)
        };

        binding.signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_loginFragment2)
        }

//        binding.guestButton.setOnClickListener{
//            findNavController().navigate(R.id.action_WelcomeFragment_to_homeFragment)
//        }

        return binding.root

    }

    fun hideBottomNavBar() {
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        view.visibility = View.GONE
    }


}