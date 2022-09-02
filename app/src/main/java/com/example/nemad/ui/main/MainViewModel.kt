package com.example.nemad.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.nemad.api.main.response.*
import com.example.nemad.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

    var searchEnabled: Boolean = false

    var complaintInfo: Pair<String?, String?> = Pair("", "")
    var orgId: Long = 0
    var leafNodeCategoryId: Long = 0

    private val _mediaResponse = MutableLiveData<Response<MediaResponse>>()
    val mediaResponse: LiveData<Response<MediaResponse>> = _mediaResponse

    private val _complaintResponse = MutableLiveData<Response<ComplaintResult>>()
    val complaintResponse: LiveData<Response<ComplaintResult>> = _complaintResponse
    private var parentCategoryList: Stack<List<Category>> = Stack()
    private var parentOrganizationalUnitsList: Stack<List<OrganizationalUnits>> = Stack()
    var organizationalUnitsList: MutableLiveData<List<OrganizationalUnits>?> = MutableLiveData()
    private var organizationalUnitsListSearch: MutableLiveData<List<OrganizationalUnits>?> =
        MutableLiveData()
    var categoryList: MutableLiveData<List<Category>?> = MutableLiveData()
    private var categoryListSearch: MutableLiveData<List<Category>?> = MutableLiveData()
    private val flattenOrgList: MutableList<OrganizationalUnits> = mutableListOf()
    private val flattenCategoryList: MutableList<Category> = mutableListOf()
    private var requestList: MutableLiveData<List<Requests>?> = MutableLiveData()
    private var orgUnitList: List<OrganizationalUnits>? = null
    private var list: List<Category>? = null
    var isLeafNode: Boolean = false
    var isOrgLeafNode: Boolean = false


    private val _query = MutableLiveData<String>()
    private val _startSearch = MutableLiveData<Boolean>()
    private val _startOrgSearch = MutableLiveData<Boolean>()


    val startOrgSearch: LiveData<Boolean> = _startOrgSearch


    private val _response = MutableLiveData<Category>()
    val response: LiveData<Category> = _response

    init {
        fetchCat()
        fetchOrgUnits()
        fetchRequests()
        _startSearch.value = false
        _startOrgSearch.value = false
    }


    fun checkIfRootNode(): Boolean {
        if (categoryList.value.isNullOrEmpty()) return false
        return when (categoryList.value?.get(0)?.parentId) {
            1L -> true
            null -> true

            else -> false
        }
    }


    private fun fetchRequests() {
        viewModelScope.launch {
            try {
                requestList.value = mainRepository.fetchRequests()
            } catch (e: java.lang.Exception) {
            }
        }
    }


    private fun fetchCat() {
        viewModelScope.launch {
            try {
                val category = mainRepository.fetchCategories()
                list = listOf(category)
                val catVal = mainRepository.fetchCategories().children
                categoryList.value = catVal
                categoryListSearch.value = catVal

            } catch (e: Exception) {
            }
        }
    }

    private fun fetchOrgUnits() {
        viewModelScope.launch {
            try {
                val organizationalUnits = mainRepository.fetchOrganizationalUnits()
                orgUnitList = listOf(organizationalUnits)
                organizationalUnitsList.value = organizationalUnits.children
                organizationalUnitsListSearch.value = organizationalUnits.children

            } catch (e: Exception) {
            }
        }
    }


    fun subList(category: Category) {
        if (!categoryList.value.isNullOrEmpty())
            parentCategoryList.add(categoryList.value)
        categoryList.value = category.children
        categoryListSearch.value = category.children
    }

    fun subOrgList(organizationalUnits: OrganizationalUnits) {
        if (!organizationalUnitsList.value.isNullOrEmpty())
            parentOrganizationalUnitsList.add(organizationalUnitsList.value)

        organizationalUnitsList.value = organizationalUnits.children
        organizationalUnitsListSearch.value = organizationalUnits.children
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

            val response = mainRepository.sendComplaint(
                managerName,
                complaintHeader,
                complaintText,
                orgId,
                leafNodeCategoryId,
                attachmentFiles
            )

            if (response.isSuccessful) complaintInfo =
                Pair(
                    response.body()!!.title.toString(), response.body()!!.trackingNumber.toString()
                )

            _complaintResponse.postValue(response)


        }
    }

    fun setAsParent() {
        val temp = parentCategoryList.pop()
        categoryList.value = temp
        categoryListSearch.value = temp

    }

    fun setAsOrgParent() {
        val temp = parentOrganizationalUnitsList.pop()
        organizationalUnitsList.value = temp
        organizationalUnitsListSearch.value = temp
    }

    fun searchOrgNow(query: String?) {
        _query.value = query!!
        //make a copy of category list
        if (!query.isNullOrEmpty())
            organizationalUnitsList.value = searchOrgQuery(query)
        else {
            organizationalUnitsList.value = organizationalUnitsListSearch.value
        }
    }

    private fun searchOrgQuery(query: String): List<OrganizationalUnits> {
        val newList: MutableList<OrganizationalUnits> = mutableListOf()
        flattenTheOrgList()

        Log.d("added", flattenOrgList.size.toString())
        flattenOrgList.forEach {
            if (it.title!!.contains(query))
                newList.add(it)
        }

        return newList
    }


    private fun flattenTheOrgList() {
        flattenOrgList.clear()
        organizationalUnitsListSearch.value!!.forEach {
            flattenRecursiveOrg(it)
        }
    }

    private fun flattenRecursiveOrg(organizationalUnits: OrganizationalUnits) {
        flattenOrgList.add(organizationalUnits)
        Log.d("added", organizationalUnits.title.toString())
        if (!organizationalUnits.children.isNullOrEmpty())
            organizationalUnits.children!!.forEach {
                flattenRecursiveOrg(it)
            }

    }


    fun searchNow(query: String?) {
        searchEnabled = true
        _query.value = query!!
        //make a copy of category list
        if (!query.isNullOrEmpty())
            categoryList.value = searchQuery(query)
        else {
            categoryList.value = categoryListSearch.value
        }


    }

    private fun searchQuery(query: String): List<Category> {
        val newList: MutableList<Category> = mutableListOf()

        flattenTheCategoryList()
//        categoryListSearch.value!!.forEach {
//            if (it.title!!.contains(query))
//                newList.add(it)
//        }

        flattenCategoryList.forEach {
            if (it.title!!.contains(query))
                newList.add(it)
        }

        return newList
    }

    private fun flattenTheCategoryList() {
        flattenCategoryList.clear()
        categoryListSearch.value!!.forEach {
            flattenRecursiveCategory(it)
        }
    }

    private fun flattenRecursiveCategory(category: Category) {
        flattenCategoryList.add(category)
        if (!category.children.isNullOrEmpty())
            category.children!!.forEach {
                flattenRecursiveCategory(it)
            }
    }


    fun startOrgSearch() {
        _startOrgSearch.value = true
    }


    fun uploadFile(file: File) {
        viewModelScope.launch {
            _mediaResponse.value = mainRepository.sendFile(file)
        }
    }

    fun searchDone() {
        _startSearch.value = false
    }

    fun searchOrgDone() {
        _startSearch.value = false
    }


}