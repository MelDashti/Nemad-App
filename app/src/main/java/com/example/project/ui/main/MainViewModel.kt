package com.example.project.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.api.main.response.*
import com.example.project.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

    var orgId: Long = 0
    var leafNodeCategoryId: Long = 0

    private val _complaintResponse = MutableLiveData<Response<ComplaintResult>>()
    val complaintResponse: LiveData<Response<ComplaintResult>> = _complaintResponse

    var parentCategoryList: Stack<List<Category>> = Stack()
    var parentOrganizationalUnitsList: Stack<List<OrganizationalUnits>> = Stack()

    var organizationalUnitsList: MutableLiveData<List<OrganizationalUnits>?> = MutableLiveData()
    var categoryList: MutableLiveData<List<Category>?> = MutableLiveData()


    var requestList: MutableLiveData<List<Requests>?> = MutableLiveData()

    var orgList: MutableLiveData<List<Organization>?> = MutableLiveData()
    private val _orgResponse = MutableLiveData<Category>()
    val orgResponse: LiveData<Category> = _orgResponse
    var test: Int = 0

    var orgUnitList: List<OrganizationalUnits>? = null
    var list: List<Category>? = null

    var isLeafNode: Boolean = false
    var isOrgLeafNode: Boolean = false

    private val _response = MutableLiveData<Category>()
    val response: LiveData<Category> = _response

    private val _showSublist = MutableLiveData<Boolean>()
    val showSublist: LiveData<Boolean> = _showSublist

    init {
        fetchCat()
        fetchOrgUnits()
        fetchRequests()
    }


    fun fetchOrg() {
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


    fun fetchRequests() {
        viewModelScope.launch {
            try {
                Log.d("hehehe", "before fetching requests")
                requestList!!.value = mainRepository.fetchRequests()
            } catch (e: java.lang.Exception) {
                Log.d("hehehe", e.localizedMessage)
            }
        }
    }


    fun fetchCat() {
        viewModelScope.launch {
            try {
                Log.d("hehehe", "before fetching")
                val category = mainRepository.fetchCategories()
                list = listOf(category)
                categoryList.value = mainRepository.fetchCategories().children

            } catch (e: Exception) {
                Log.d("hehehe", e.localizedMessage)
            }
        }
    }

    fun fetchOrgUnits() {
        viewModelScope.launch {
            try {
                Log.d("hehehe", "before fetching")
                val organizationalUnits = mainRepository.fetchOrganizationalUnits()
                orgUnitList = listOf(organizationalUnits)
                organizationalUnitsList.value = mainRepository.fetchOrganizationalUnits().children

            } catch (e: Exception) {
                Log.d("hehehe", e.localizedMessage)
            }
        }
    }

    fun showSublist() {
        _showSublist.value = true
    }

    fun subList(category: Category) {
        if (!categoryList.value.isNullOrEmpty())
            parentCategoryList.add(categoryList.value)
        categoryList.value = category.children
    }

    fun subOrgList(organizationalUnits: OrganizationalUnits) {
        if (!organizationalUnitsList.value.isNullOrEmpty())
            parentOrganizationalUnitsList.add(organizationalUnitsList.value)
        organizationalUnitsList.value = organizationalUnits.children
    }

    fun changeTest() {
        test = 99

    }

    fun clearData() {
        categoryList.value = null
        orgId = 0
        leafNodeCategoryId = 0
    }

    fun clearOrgData() {
//        organizationalUnitsList.value = null
        orgId = 0
    }

//    fun getCategory(currentParentId: Long) {
//        viewModelScope.launch {
//            mainRepository.getCategory(currentParentId)
//
//        }
//
//    }

    fun sendRequest(managerName: String, complaintHeader: String, complaintText: String) {
        viewModelScope.launch {

            mainRepository.sendComplaint(
                managerName,
                complaintHeader,
                complaintText,
                orgId,
                leafNodeCategoryId
            )
        }
    }

    fun setAsParent() {
        categoryList.value = parentCategoryList.pop() as List<Category>?

    }

    fun setAsOrgParent() {
        organizationalUnitsList.value =
            parentOrganizationalUnitsList.pop() as List<OrganizationalUnits>?

    }

    fun uploadFile(toString: String) {
        viewModelScope.launch {
            mainRepository.sendFile(toString)
        }
    }


}