package com.example.project.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.BaseFragment
import com.example.project.R
import com.example.project.databinding.FragmentRegisterBinding
import com.example.project.ui.main.MainViewModel
import com.example.project.viewmodels.AuthSharedViewModel
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

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            Log.d("jello", "why")
            if (it.code() == 428) {
                findNavController().navigate(R.id.action_RegisterFragment_to_verificationFragment)
                Toast.makeText(requireContext(), "کد ارسال شد", Toast.LENGTH_SHORT).show()
                viewModel.verificationType = 0
                Log.d("code", "428")
            } else if (it.code() == 406) {
                Toast.makeText(
                    requireContext(),
                    "کاربری با این نام کاربری پیش از این ثبت شده است.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("code", "406")
            } else {
                Toast.makeText(requireContext(), "خطا سرور", Toast.LENGTH_SHORT).show()
            }


        })
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }


}