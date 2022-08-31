package com.example.project.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.api.main.response.MediaResponse
import com.example.project.api.main.response.UserInfo
import com.example.project.repository.AuthRepository
import com.example.project.repository.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    private val _tokenCheckResponse = MutableLiveData<Response<UserInfo>>()
    val tokenCheckResponse: LiveData<Response<UserInfo>> = _tokenCheckResponse

    private val _tokenChecked = MutableLiveData<Boolean>()
    val tokenChecked: LiveData<Boolean> = _tokenChecked

    init {
//        checkIfTokenStillValid()
        _tokenChecked.value = false
    }

    fun checkIfTokenStillValid() {
        viewModelScope.launch {
            try {
                Log.d("helllo", "whaaaat")
                val response = authRepository.checkTokenValid()
                _tokenChecked.value = response.code() == 200
                Log.d("helllo", response.code().toString())
                Log.d("helllo", _tokenChecked.value.toString())
            } catch (e: Exception) {
            }
        }

    }


}