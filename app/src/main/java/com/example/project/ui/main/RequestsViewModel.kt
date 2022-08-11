package com.example.project.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.project.api.main.response.*
import com.example.project.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.concurrent.Task
import retrofit2.Response
import java.io.File
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {


    var requestList: MutableLiveData<List<Requests>?> = MutableLiveData()
    var requests: MutableLiveData<Requests> = MutableLiveData()

    init {
        fetchRequests()
    }

    private fun fetchRequests() {
        viewModelScope.launch {
            try {
                requestList.value = mainRepository.fetchRequests()
            } catch (e: java.lang.Exception) {
            }
        }
    }

}