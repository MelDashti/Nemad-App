package com.example.project.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.project.R
import com.example.project.adapter.MediaFileAdapter
import com.example.project.adapter.ProceedingItemAdapter
import com.example.project.api.main.response.Proceeding
import com.example.project.databinding.FragmentMyRequestBinding
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
//        val arr = mutableListOf<Proceeding>(Proceeding(), Proceeding(), Proceeding())
//        adapter.submitList(arr)

//        if (viewModel.requests.value?.proceedings?.isEmpty() == true) {
//        }


        lifecycleScope.launch {
            viewModel.fetchCurrentReq()
        }


        if (viewModel.requests.value!!.status == 4L) {
            binding.ratingLayout.visibility = View.VISIBLE
            binding.sendRequest.visibility = View.VISIBLE
        }


        if (viewModel.requests.value!!.status == 2L) {
            binding.confrim.visibility = View.VISIBLE
            binding.objectionButton.visibility = View.VISIBLE
        }


        binding.confrim.setOnClickListener {
            findNavController().navigate(R.id.action_myRequestFragment_to_myDialog)
        }


        binding.sendRequest.setOnClickListener {
            val rating = binding.ratingBar.rating
            Log.d("hdafh", binding.ratingBar.rating.toString())
            if (rating == 0F) {
                Toast.makeText(requireContext(), "امتیازی دریافت نشد", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.sendRating()
                binding.ratingLayout.visibility = View.GONE
                binding.sendRequest.visibility = View.GONE
                Toast.makeText(requireContext(), "ممنون", Toast.LENGTH_SHORT).show()
            }
        }


        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                com.example.project.R.drawable.divider
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