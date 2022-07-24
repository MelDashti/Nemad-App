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
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {


    var test: Int = 0
    var list: List<Category>? = null

    var orgList: MutableList<Organization>? = null


    private val _response = MutableLiveData<Category>()
    val response: LiveData<Category> = _response

    private val _showSublist = MutableLiveData<Boolean>()
    val showSublist: LiveData<Boolean> = _showSublist

    init {
        fetchCat()
        fetchOrg()
    }

    fun fetchOrg() {
        viewModelScope.launch {
            try {
                Log.d("hehehe", "before fetching ord")
                orgList = mainRepository.fetchOrganization()
            } catch (e: java.lang.Exception) {
                Log.d("hehehe", e.localizedMessage)
            }
        }
    }


    private fun fetchCat() {
        viewModelScope.launch {
            try {
                Log.d("hehehe", "before fetching")
                val category = mainRepository.fetchCategories()
                list = listOf(category)

            } catch (e: Exception) {
                Log.d("hehehe", e.localizedMessage)
            }
        }
    }


    fun showSublist() {
        _showSublist.value = true
    }

    fun subList(category: Category): List<Category>? {
        return category.children

    }

    fun changeTest() {
        test = 99

    }


}