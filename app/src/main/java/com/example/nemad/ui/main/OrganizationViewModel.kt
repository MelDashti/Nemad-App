package com.example.nemad.ui.main

import androidx.lifecycle.ViewModel
import com.example.nemad.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrganizationViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {




    init {

    }



}