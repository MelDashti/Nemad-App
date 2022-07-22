package com.example.internproject.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.internproject.api.auth.responses.AuthenticationResult
import com.example.internproject.repository.AuthRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    private val _response = MutableLiveData<AuthenticationResult>()
    val response: LiveData<AuthenticationResult> = _response


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

        }
    }


}