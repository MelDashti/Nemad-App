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
import java.io.File
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

    var orgId: Long = 0
    var leafNodeCategoryId: Long = 0

    private val _mediaResponse = MutableLiveData<Response<MediaResponse>>()
    val mediaResponse: LiveData<Response<MediaResponse>> = _mediaResponse

    private val _complaintResponse = MutableLiveData<Response<ComplaintResult>>()
    val complaintResponse: LiveData<Response<ComplaintResult>> = _complaintResponse

    private var parentCategoryList: Stack<List<Category>> = Stack()
    private var parentOrganizationalUnitsList: Stack<List<OrganizationalUnits>> = Stack()

    var organizationalUnitsList: MutableLiveData<List<OrganizationalUnits>?> = MutableLiveData()
    var categoryList: MutableLiveData<List<Category>?> = MutableLiveData()


    var requestList: MutableLiveData<List<Requests>?> = MutableLiveData()

    private var orgList: MutableLiveData<List<Organization>?> = MutableLiveData()

    private var orgUnitList: List<OrganizationalUnits>? = null
    private var list: List<Category>? = null

    var isLeafNode: Boolean = false
    var isOrgLeafNode: Boolean = false

    private val _clearRecyclerView = MutableLiveData<Boolean>()
    val clearRecyclerView: LiveData<Boolean> = _clearRecyclerView


    private val _response = MutableLiveData<Category>()
    val response: LiveData<Category> = _response


    init {
        fetchCat()
        fetchOrgUnits()
        fetchRequests()
    }


//    fun fetchOrg() {
//        viewModelScope.launch {
//            try {
//                orgList.value = mainRepository.fetchOrganization()
//            } catch (e: java.lang.Exception) {
//            }
//        }
//    }


    private fun fetchRequests() {
        viewModelScope.launch {
            try {
                requestList.value = mainRepository.fetchRequests()
            } catch (e: java.lang.Exception) {
            }
        }
    }


    fun fetchCat() {
        viewModelScope.launch {
            try {
                val category = mainRepository.fetchCategories()
                list = listOf(category)
                categoryList.value = mainRepository.fetchCategories().children

            } catch (e: Exception) {
            }
        }
    }

    private fun fetchOrgUnits() {
        viewModelScope.launch {
            try {
                val organizationalUnits = mainRepository.fetchOrganizationalUnits()
                orgUnitList = listOf(organizationalUnits)
                organizationalUnitsList.value = mainRepository.fetchOrganizationalUnits().children

            } catch (e: Exception) {
            }
        }
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


    fun clearData() {
        categoryList.value = null
        orgId = 0
        leafNodeCategoryId = 0
    }


    fun sendRequest(
        managerName: String,
        complaintHeader: String,
        complaintText: String,
        attachmentFiles: List<String>
    ) {
        viewModelScope.launch {

            mainRepository.sendComplaint(
                managerName,
                complaintHeader,
                complaintText,
                orgId,
                leafNodeCategoryId,
                attachmentFiles
            )
        }
    }

    fun setAsParent() {
        categoryList.value = parentCategoryList.pop()

    }

    fun setAsOrgParent() {
        organizationalUnitsList.value =
            parentOrganizationalUnitsList.pop()

    }

    fun uploadFile(file: File) {
        viewModelScope.launch {
            _mediaResponse.value = mainRepository.sendFile(file)
        }
    }

    fun clearAllData() {
        orgList.value = null
        categoryList.value = null
        orgUnitList = null
        orgId = 0
        leafNodeCategoryId = 0
        parentOrganizationalUnitsList.clear()
        organizationalUnitsList.value = null
        list = null
        isLeafNode = false
        isOrgLeafNode = false
        parentCategoryList.clear()
        _clearRecyclerView.value = true
        Log.d("fdsaf", "inside model right now")
        Log.d("fdsaf", clearRecyclerView.value.toString())


    }


}