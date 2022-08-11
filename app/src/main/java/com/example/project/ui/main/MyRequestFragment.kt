package com.example.project.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.databinding.FragmentMyRequestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRequestFragment : Fragment() {

    val viewModel: RequestsViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }

    lateinit var binding: FragmentMyRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyRequestBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        Log.d("hehe", viewModel.requests.value.toString())

        when (viewModel.requests.value!!.statusStr) {
            "WaitingForAcceptance" -> {
                binding.toggle.setImageResource(R.drawable.waitingforacceptance)
                binding.statusTitle.text = "در انتزار تایید"
            }
            "WaitingForConfirmation" -> {
                binding.toggle.setImageResource(R.drawable.waitingforconfirmation)
                binding.statusTitle.text = "بررسی شده"
            }

            "UnderOrganizationalInspection" -> {
                binding.toggle.setImageResource(R.drawable.underorganizatoininspection)
                binding.statusTitle.text = "در حال بررسی"
            }
            "Done" -> {
                binding.toggle.setImageResource(R.drawable.done)
                binding.statusTitle.text = "انجام شده"
            }
        }


        return binding.root
    }

}