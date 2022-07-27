package com.example.project.ui.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project.R
import com.example.project.viewmodels.ResetViewModel

class ResetFragment : Fragment() {

    companion object {
        fun newInstance() = ResetFragment()
    }

    private lateinit var viewModel: ResetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reset_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResetViewModel::class.java)
        // TODO: Use the ViewModel
    }

}