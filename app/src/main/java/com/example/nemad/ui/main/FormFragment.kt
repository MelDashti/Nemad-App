package com.example.nemad.ui.main

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
import com.example.nemad.R
import com.example.nemad.databinding.FormFragmentBinding
import android.app.Activity
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import com.example.nemad.adapter.MediaFileAdapter
import com.example.nemad.api.main.response.MediaResponse
import com.example.nemad.util.FileUtil.from
import com.google.android.material.snackbar.Snackbar


class FormFragment : Fragment() {

    val viewModel: MainViewModel by navGraphViewModels(R.id.navigation2) { defaultViewModelProviderFactory }
    lateinit var binding: FormFragmentBinding
    private var attachmentFiles: MutableList<String> = mutableListOf()
    private var selectFiles: MutableList<MediaResponse> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FormFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.sendRequest.setOnClickListener {

            val managerName = binding.managerNameTextInput.text.toString().trim()
            val complaintHeader = binding.complaintHeaderInputText.text.toString().trim()
            val complaintText = binding.complaintTextInput.text.toString().trim()
            when {
                complaintHeader.isEmpty() -> binding.complaintHeaderInputText.error =
                    "این فیلد الزامی است"
                managerName.isEmpty() -> binding.managerNameTextInput.error = "این فیلد الزامی است"

                complaintText.isEmpty() -> binding.complaintTextInput.error = "این فیلد الزامی است"
                else -> viewModel.sendRequest(
                    managerName,
                    complaintHeader,
                    complaintText,
                    attachmentFiles.toList()
                )
            }
        }

        viewModel.complaintResponse.observe(viewLifecycleOwner, {

            if (it.isSuccessful) {
                Log.d("youo", it.body()?.trackingNumber.toString())
                Snackbar.make(binding.root, "درخواست شما با موفقیت ارسال شد", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.successful)
                    )
                    .show()
                findNavController().navigate(R.id.action_formFragment_to_requestConfirmation2)

            } else {
                Log.d("youo", "yep")

                Snackbar.make(binding.root, "خطا سرور", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.error)
                    )
                    .show()
            }

        })

        if (selectFiles.isEmpty()) {
            binding.uploadedFileRecyclerVIew.visibility = View.INVISIBLE
            binding.uploadedFileName.visibility = View.VISIBLE

        }


        val adapter = MediaFileAdapter()
        binding.uploadedFileRecyclerVIew.adapter = adapter

        viewModel.mediaResponse.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                val mediaId = it.body()!!.id.toString()
                attachmentFiles.add(mediaId)
//                binding.uploadedFileName.text = attachmentFiles.toString()
                if (binding.uploadedFileRecyclerVIew.visibility == View.INVISIBLE) {
                    binding.uploadedFileRecyclerVIew.visibility = View.VISIBLE
                    binding.uploadedFileName.visibility = View.INVISIBLE
                }

                selectFiles.add(it.body()!!)
                adapter.submitList(selectFiles)
                adapter.notifyDataSetChanged()

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




        return binding.root
    }


}