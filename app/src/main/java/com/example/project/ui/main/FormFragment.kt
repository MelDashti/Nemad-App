package com.example.project.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.project.R
import com.example.project.databinding.FormFragmentBinding
import android.app.Activity
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.example.project.util.FileUtil.from


class FormFragment : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.navigation2) { defaultViewModelProviderFactory }
    lateinit var binding: FormFragmentBinding
    private var attachmentFiles: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FormFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        binding.sendRequest.setOnClickListener {

            val managerName = binding.managerNameTextInput.text.toString().trim()
            val complaintHeader = binding.complaintHeaderInputText.text.toString().trim()
            val complaintText = binding.complaintTextInput.text.toString().trim()
            viewModel.sendRequest(
                managerName,
                complaintHeader,
                complaintText,
                attachmentFiles.toList()
            )

        }

        viewModel.complaintResponse.observe(viewLifecycleOwner, {
            Log.d("youo","na")

            if (it.isSuccessful) {
                Log.d("youo","success")
                Toast.makeText(
                    requireContext(),
                    "درخواست شما با موفقیت ارسال شد",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.d("youo","yep")

                Toast.makeText(
                    requireContext(),
                    "خظا سرور",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

        viewModel.mediaResponse.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                val mediaId = it.body()!!.id.toString()
                Toast.makeText(requireContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                attachmentFiles.add(mediaId)
                binding.uploadedFileName.text = attachmentFiles.toString()

            } else Toast.makeText(requireContext(), "Upload Failed", Toast.LENGTH_SHORT).show()
        })


        val sActivityResultLauncher = registerForActivityResult(
            StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent = result.data!!
                val uri: Uri? = data.data
                val file = from(requireContext(), uri!!)
                Log.d(
                    "file",
                    "File...:::: uti - " + file.path + " file -" + file + " : " + file.exists()
                )

                viewModel.uploadFile(file)


            }
        }


        binding.uploadFile.setOnClickListener {
            // when user clicks on upload file, open file picker
            var data = Intent(Intent.ACTION_OPEN_DOCUMENT)
            data.type = "*/*"
            data = Intent.createChooser(data, "Choose a file")
            sActivityResultLauncher.launch(data)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }


}