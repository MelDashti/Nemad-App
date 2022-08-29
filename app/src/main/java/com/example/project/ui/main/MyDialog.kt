package com.example.project.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.databinding.MyDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyDialog : BottomSheetDialogFragment() {


    val viewModel: RequestsViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }
    lateinit var binding: MyDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyDialogBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.uploadFile.setOnClickListener {

            val rating = binding.ratingBar.rating
            Log.d("hdafh", binding.ratingBar.rating.toString())
            if (rating == 0F) {
                Toast.makeText(requireContext(), "امتیازی دریافت نشد", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.sendRating(rating.toLong())

            }

            viewModel.ratingResponse.observe(viewLifecycleOwner, {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "موفق", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        viewModel.refreshCurrentRequest()
                    }
                    viewModel.showSnackbar()

                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "مشکل سرور", Toast.LENGTH_SHORT).show()

                }
            })

        }




        return binding.root
    }
}