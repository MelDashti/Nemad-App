package com.example.project.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.project.api.auth.responses.AuthenticationResult
import com.example.project.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthSharedViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    private val _onClickConfirm = MutableLiveData<Boolean>()
    val onClickConfirm: LiveData<Boolean> = _onClickConfirm

    private val _registerResponse = MutableLiveData<Response<AuthenticationResult>>()
    val registerResponse: LiveData<Response<AuthenticationResult>> = _registerResponse

    private val _verifyResponse = MutableLiveData<Response<ResponseBody>>()
    val verifyResponse: LiveData<Response<ResponseBody>> = _verifyResponse

    var _userPass: MutableLiveData<Pair<String, String>> = MutableLiveData()


    private val _onClickRegister = MutableLiveData<Boolean>()

    val onClickRegister: LiveData<Boolean> = _onClickRegister


    private val _response = MutableLiveData<Response<AuthenticationResult>>()
    val response: LiveData<Response<AuthenticationResult>> = _response

    private val _buttonClicked = MutableLiveData<Boolean>()
    val loginButtonCLicked: LiveData<Boolean> = _buttonClicked

    override fun onCleared() {
        super.onCleared()
    }

    init {
    }

    fun onLoginClicked() {
        _buttonClicked.value = true;
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                _response.value = authRepository.login(username, password)
            } catch (e: Exception) {
                Log.d("status", e.localizedMessage)
            }
        }
    }


    fun onConfirmedClicked() {
        _onClickConfirm.value = true
    }


    fun onClickRegister() {
        _onClickRegister.value = true;
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            try {
                Log.d("status", username)
                _registerResponse.value = authRepository.register(username, password)
            } catch (e: Exception) {

            }
        }


    }

    fun postCode(code: String) {
        TODO("Not yet implemented")
    }


    fun verify(code: String) {
        viewModelScope.launch {
            try {
                authRepository.verifyCode(_userPass.value!!.first, _userPass.value!!.second, code)
            } catch (e: java.lang.Exception) {
            }
        }
    }

    fun sendPhoneNumber(phoneNo: String) {
        viewModelScope.launch {
            authRepository.sendPhoneNumber(phoneNo)

        }
    }


}