package com.example.nemad.ui.main

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.nemad.adapter.PassengersDataSource
import com.example.nemad.api.main.MainApiService
import com.example.nemad.api.main.response.*
import com.example.nemad.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    val mainRepository: MainRepository,
    val mainApiService: MainApiService
) : ViewModel() {


    private val _ratingResponse = MutableLiveData<Response<ResponseBody>>()
    val ratingResponse: LiveData<Response<ResponseBody>> = _ratingResponse


    private val _showSnackBar = MutableLiveData<Boolean>()
    val showSnackbar: LiveData<Boolean> = _showSnackBar

    var recentRequestList: MutableLiveData<List<Requests>?> = MutableLiveData()
    var requests: MutableLiveData<Requests> = MutableLiveData()
    var requestsPagedList = mainRepository.fetchPagedReq(viewModelScope)
    var lolList = Pager(PagingConfig(pageSize = 10)) {
        PassengersDataSource(mainApiService)
    }.flow.cachedIn(viewModelScope)


    suspend fun fetchCurrentReq() {
        viewModelScope.launch {
            try {
                requests.value = mainRepository.fetchCurrentReq(requests.value!!.id)
            } catch (e: java.lang.Exception) {
            }
        }
    }


    init {
        fetchRecentRequests()
    }

    suspend fun refreshCurrentRequest() {
        fetchCurrentReq()
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

    fun sendRating(rating: Long) {
        viewModelScope.launch {
            try {
                _ratingResponse.value = mainRepository.sendRating(requests.value!!.id, rating)

            } catch (e: java.lang.Exception) {

            }
        }

    }

    fun showSnackbarDone() {
        _showSnackBar.value = false
    }

    fun showSnackbar() {
        _showSnackBar.value = true
    }


}