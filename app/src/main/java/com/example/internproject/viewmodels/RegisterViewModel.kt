package com.example.internproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    private val _onClickRegister = MutableLiveData<Boolean>()

    val onClickRegister: LiveData<Boolean> = _onClickRegister

    init {


    }

    fun onClickRegister() {
        _onClickRegister.value = true;
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            try {
                Log.d("status", username)
                authRepository.register(username, password)
            } catch (e: Exception) {
                Log.d("status", e.localizedMessage)

            }
        }


    }


}