package com.example.project.ui.main

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.project.adapter.PassengersDataSource
import com.example.project.api.main.MainApiService
import com.example.project.api.main.response.*
import com.example.project.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    val mainRepository: MainRepository,
    val mainApiService: MainApiService
) : ViewModel() {


    var requestList: MutableLiveData<List<Requests>?> = MutableLiveData()
    var recentRequestList: MutableLiveData<List<Requests>?> = MutableLiveData()


    var requests: MutableLiveData<Requests> = MutableLiveData()


    var requestsPagedList = mainRepository.fetchPagedReq(viewModelScope)

    var lolList = Pager(PagingConfig(pageSize = 10)) {
        PassengersDataSource(mainApiService)
    }.flow.cachedIn(viewModelScope)


    private fun fetchPagedReq() {

    }

    init {
        fetchRequests()
//        fetchRecentRequests()
    }

    private fun fetchRequests() {
        viewModelScope.launch {
            try {
                requestList.value = mainRepository.fetchRequests()
            } catch (e: java.lang.Exception) {
            }
        }
    }

    fun refreshRecentRequests() {
        fetchRecentRequests()
    }


    private fun fetchRecentRequests() {
        viewModelScope.launch {
            try {
                recentRequestList.value = mainRepository.fetchRecentRequests()
            } catch (e: java.lang.Exception) {
            }
        }
    }

    fun sendRating() {
        viewModelScope.launch {
            try {


            } catch (e: java.lang.Exception) {

            }
        }

    }

}