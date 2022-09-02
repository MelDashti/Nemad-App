package com.example.nemad.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.nemad.R
import com.example.nemad.adapter.ProceedingItemAdapter
import com.example.nemad.databinding.FragmentMyRequestBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyRequestFragment : Fragment() {
    val viewModel: RequestsViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }
    lateinit var binding: FragmentMyRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyRequestBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val adapter = ProceedingItemAdapter()
        binding.proceedingsRecyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.fetchCurrentReq()
        }

        if (viewModel.requests.value!!.status == 4L && viewModel.requests.value!!.rating == 0L) {
            binding.sendRequest.visibility = View.VISIBLE
        }


        if (viewModel.requests.value!!.status == 2L && viewModel.requests.value!!.rating == 0L) {
            binding.confrim.visibility = View.VISIBLE
            binding.objectionButton.visibility = View.VISIBLE
        }

        if (viewModel.requests.value!!.rating == 0L) {
            binding.ratings.visibility = View.GONE
        } else {
            binding.ratingBar2.rating = viewModel.requests.value!!.rating.toFloat()

        }

        viewModel.showSnackbar.observe(viewLifecycleOwner, Observer {
            if (it) {
                Snackbar.make(binding.root, "عملیات با موفقیت انجام شد", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.successful)
                    )
                    .show()
                binding.ratings.visibility = View.VISIBLE
                binding.ratingBar2.rating = viewModel.requests.value!!.rating.toFloat()
                binding.confrim.visibility = View.GONE
                binding.objectionButton.visibility = View.GONE
                binding.sendRequest.visibility = View.GONE
                viewModel.refreshRecentRequests()

                viewModel.showSnackbarDone()
            }

        })


        binding.confrim.setOnClickListener {
            findNavController().navigate(R.id.action_myRequestFragment_to_myDialog)
        }


        binding.sendRequest.setOnClickListener {
            findNavController().navigate(R.id.action_myRequestFragment_to_myDialog)
        }


        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                com.example.nemad.R.drawable.divider
            )!!
        )

        binding.proceedingsRecyclerView.addItemDecoration(itemDecorator)


//        proceedings is empty on server, so for the time being I have commented this
        viewModel.requests.observe(viewLifecycleOwner, {
            adapter.submitList(it.proceedings)
        })


        return binding.root
    }
}