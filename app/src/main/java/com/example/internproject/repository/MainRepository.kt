package com.example.internproject.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.internproject.api.main.MainApiService
import com.example.internproject.api.main.response.Category
import com.example.internproject.api.main.response.Organization
import com.example.internproject.api.main.response.OrganizationalUnits
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainApiService: MainApiService,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun fetchCategories(): Category {
        Log.d("hehehe", "before connecting to api")
        var response = mainApiService.getCategories()
        return response
    }

    suspend fun fetchOrganizationalUnits(): OrganizationalUnits {
        Log.d("hehehe", "before connecting to api")
        var response = mainApiService.getOrganizationUnits()
        return response
    }

    suspend fun fetchOrganization(): MutableList<Organization> {
        var response = mainApiService.getOrganizations()
        return response
    }

    fun sendComplaint(
        managerName: String,
        complaintHeader: String,
        complaintText: String,
        orgId: Long,
        leafNodeCategoryId: Long
    ) {
    }


}