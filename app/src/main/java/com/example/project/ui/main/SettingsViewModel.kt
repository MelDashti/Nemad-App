package com.example.project.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.api.main.response.UserInfo
import com.example.project.repository.AuthRepository
import com.example.project.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    var settings: MutableLiveData<UserInfo?> = MutableLiveData()


    private val _userNameResponse = MutableLiveData<Event<Response<ResponseBody>>>()
    val userNameResponse: LiveData<Event<Response<ResponseBody>>> = _userNameResponse


    private val _userNationalIdResponse = MutableLiveData<Event<Response<ResponseBody>>>()
    val userNationalIdResponse: LiveData<Event<Response<ResponseBody>>> = _userNameResponse

    init {
        fetchSettings()
    }

    fun refreshSettingFragment() {
        fetchSettings()
    }


    fun fetchSettings() {
        viewModelScope.launch {
            settings.value = authRepository.getUserInfo()
        }
    }


    fun signout() {
        authRepository.signout()
    }

    fun setUserName(firstName: String, lastName: String) {
        viewModelScope.launch {
            _userNameResponse.value = Event(authRepository.setUserName(firstName, lastName))
        }
    }

    fun setNationalId(nationalId: String) {
        viewModelScope.launch {
            _userNationalIdResponse.value = Event(authRepository.setNationalId(nationalId))
        }
    }

    fun setNewPassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {
            authRepository.setPassword(currentPassword, newPassword)
        }

    }

    fun updateUserName(firstName: String, lastName: String) {
        settings.value?.firstName = firstName
        settings.value?.lastName = lastName
    }


}