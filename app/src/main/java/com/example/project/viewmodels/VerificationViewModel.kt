package com.example.project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.api.auth.responses.AuthenticationResult
import com.example.project.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {


    private val _response = MutableLiveData<Response<ResponseBody>>()
    val response: LiveData<Response<ResponseBody>> = _response

    fun verify(code: String) {
        viewModelScope.launch {
            try {
                authRepository.verifyCode()
            } catch (e: Exception) {

            }
        }
    }


}