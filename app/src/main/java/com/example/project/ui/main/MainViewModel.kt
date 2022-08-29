package com.example.project.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.project.api.main.response.*
import com.example.project.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.concurrent.Task
import retrofit2.Response
import java.io.File
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

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
    var organizationalUnitsListSearch: MutableLiveData<List<OrganizationalUnits>?> =
        MutableLiveData()


    var categoryList: MutableLiveData<List<Category>?> = MutableLiveData()
    var categoryListSearch: MutableLiveData<List<Category>?> = MutableLiveData()


    var requestList: MutableLiveData<List<Requests>?> = MutableLiveData()

    private var orgList: MutableLiveData<List<Organization>?> = MutableLiveData()

    private var orgUnitList: List<OrganizationalUnits>? = null
    private var list: List<Category>? = null

    var isLeafNode: Boolean = false
    var isOrgLeafNode: Boolean = false

    private val _query = MutableLiveData<String>()
    private val _startSearch = MutableLiveData<Boolean>()
    private val _startOrgSearch = MutableLiveData<Boolean>()

    // related to search
//    val searchResultList: LiveData<MutableList<Category>> =
//        Transformations.switchMap(_query, ::search)
    val startSearch: LiveData<Boolean> = _startSearch
    val startOrgSearch: LiveData<Boolean> = _startOrgSearch
//
//    private fun search(query: String?) {
//        val result: MutableList<Category> = mutableListOf()
//        if (!query.isNullOrEmpty()) {
//            categoryList.value!!.forEach {
//                if (it.title!! == query)
//                    result.add(it)
//            }
//            categoryList.postValue(result)
//        }
//    }


    private val _clearRecyclerView = MutableLiveData<Boolean>()
    val clearRecyclerView: LiveData<Boolean> = _clearRecyclerView


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
        var rootNode = categoryList.value?.get(0)?.parentId
        return when (rootNode) {
            1L -> true
            null -> true
            else -> false
        }
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

    private fun fetchCurrentOrgUnits() {
        viewModelScope.launch {
            try {
                val organizationalUnits =
                    mainRepository.fetchCurrentOrganizationalUnits(organizationalUnitsList.value!![0].parentId)
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

            Log.d("hfdahs", complaintInfo.first.toString())
            Log.d("hfdahs", complaintInfo.second.toString())


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
//            organizationalUnitsList.value = organizationalUnitsListSearch.value
            if (organizationalUnitsList.value!![0].parentId == null) fetchOrgUnits()
            else
                fetchCurrentOrgUnits()
        }
    }

    private fun searchOrgQuery(query: String): List<OrganizationalUnits> {
        val newList: MutableList<OrganizationalUnits> = mutableListOf()
        organizationalUnitsListSearch.value!!.forEach {
            if (it.title!!.contains(query))
                newList.add(it)
        }
        return newList
    }


    fun searchNow(query: String?) {
        _query.value = query!!
        //make a copy of category list
        if (!query.isNullOrEmpty())
            categoryList.value = searchQuery(query)
        else {

        }


    }

    private fun searchQuery(query: String): List<Category> {
        val newList: MutableList<Category> = mutableListOf()
        categoryListSearch.value!!.forEach {
            if (it.title!!.contains(query))
                newList.add(it)
        }
        return newList
    }


    fun startOrgSearch() {
        _startOrgSearch.value = true
    }

    fun startCategorySearch() {
        _startSearch.value = true
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

    fun searchDone() {
        _startSearch.value = false
    }

    fun searchOrgDone() {
        _startSearch.value = false
    }


}