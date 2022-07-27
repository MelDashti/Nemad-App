package com.example.project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {


    fun verify() {
        viewModelScope.launch {
            try {
                authRepository.verifyCode()
            } catch (e: Exception){

            }
        }
    }


}