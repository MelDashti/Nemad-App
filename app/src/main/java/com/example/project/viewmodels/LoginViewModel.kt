package com.example.project.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.project.api.auth.responses.AuthenticationResult
import com.example.project.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

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


}