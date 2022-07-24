package com.example.internproject.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.api.main.response.Category
import com.example.internproject.api.main.response.Organization
import com.example.internproject.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrganizationViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {


    var orgList: MutableLiveData<List<Organization>?> = MutableLiveData()


    private val _response = MutableLiveData<Category>()
    val response: LiveData<Category> = _response


    init {
        fetchOrg()
    }

    fun fetchOrg()  {
        viewModelScope.launch {
            try {
                Log.d("hehehe", "before fetching ord")
                orgList!!.value = mainRepository.fetchOrganization()
                Log.d("hehehe", orgList!!.value!!.size.toString())
            } catch (e: java.lang.Exception) {
                Log.d("hehehe", e.localizedMessage)
            }
        }
    }



}