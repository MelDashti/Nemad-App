package com.example.internproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class RegisterViewModel constructor() : ViewModel() {

    private val _onClickRegister = MutableLiveData<Boolean>()

    val onClickRegister: LiveData<Boolean> = _onClickRegister

    init {


    }

    fun onClickRegister(){
        _onClickRegister.value = true;
    }





}