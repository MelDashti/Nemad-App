package com.example.project.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.api.main.response.UserInfo
import com.example.project.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    var settings: MutableLiveData<UserInfo?> = MutableLiveData()

    init {
        fetchSettings()
    }

    private fun fetchSettings() {
        viewModelScope.launch {
            settings.value = authRepository.getUserInfo()

        }


    }

    fun signout() {
        authRepository.signout()
    }


}