package com.example.internproject.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.api.auth.responses.AuthenticationResult
import com.example.internproject.api.main.response.CategoryResponse
import com.example.internproject.domain.Category
import com.example.internproject.repository.AuthRepository
import com.example.internproject.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

    //    var categoryItems = fetchCat()
    private val _response = MutableLiveData<CategoryResponse>()
    val response: LiveData<CategoryResponse> = _response

    init {
        fetchCat()
    }


    private fun fetchCat() {
        viewModelScope.launch {
            try {
                Log.d("hehehe", "before fetching")
                _response.value = mainRepository.fetchCategories()

            } catch (e: Exception) {
                Log.d("hehehe", e.localizedMessage)
            }
        }
    }


}