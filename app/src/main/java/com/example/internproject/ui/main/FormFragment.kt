package com.example.internproject.ui.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.internproject.R
import com.example.internproject.databinding.FormFragmentBinding
import android.app.Activity
import android.net.Uri

import androidx.activity.result.ActivityResultCallback

import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import java.lang.Exception
import java.lang.RuntimeException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class FormFragment : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph) { defaultViewModelProviderFactory }
    lateinit var binding: FormFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FormFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        Log.d("ID", viewModel.orgId.toString())
        Log.d("ID", viewModel.leafNodeCategoryId.toString())


        binding.sendRequest.setOnClickListener {
            val managerName = binding.managerNameTextInput.text.toString().trim()
            val complaintHeader = binding.complaintHeaderInputText.toString().trim()
            val complaintText = binding.complaintTextInput.toString().trim()

            viewModel.sendRequest(managerName, complaintHeader, complaintText)

        }

        viewModel.complaintResponse.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Complaint sent", Toast.LENGTH_SHORT)
            }

        })









        val sActivityResultLauncher = registerForActivityResult(
            StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.getResultCode() === Activity.RESULT_OK) {
                    val data: Intent = result.getData()!!
                    val uri: Uri? = data.data
                }
            })


        binding.uploadFile.setOnClickListener {
            // when user clicks on upload file, open file picker
            var data = Intent(Intent.ACTION_OPEN_DOCUMENT)
            data.setType("*/*")
            data = Intent.createChooser(data, "Choose a file")
            sActivityResultLauncher.launch(data)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file
            Toast.makeText(requireContext(), "File name", Toast.LENGTH_SHORT)
        }
    }
}