package com.example.internproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.api.auth.responses.AuthenticationResult
import com.example.internproject.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    private val _onClickConfirm = MutableLiveData<Boolean>()
    val onClickConfirm: LiveData<Boolean> = _onClickConfirm

    private val _response = MutableLiveData<Response<AuthenticationResult>>()
    val response: LiveData<Response<AuthenticationResult>> = _response


    private val _onClickRegister = MutableLiveData<Boolean>()

    val onClickRegister: LiveData<Boolean> = _onClickRegister

    init {
        // if register s

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
                _response.value = authRepository.register(username, password)
            } catch (e: Exception) {
                Log.d("status", e.localizedMessage)

            }
        }


    }

    fun postCode(code: String) {
        TODO("Not yet implemented")
    }


}