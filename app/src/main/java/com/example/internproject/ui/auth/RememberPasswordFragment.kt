package com.example.internproject.ui.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.internproject.R
import com.example.internproject.viewmodels.RememberPasswordViewModel

class RememberPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = RememberPasswordFragment()
    }

    private lateinit var viewModel: RememberPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.remember_password_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RememberPasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}