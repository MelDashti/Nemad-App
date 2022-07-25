package com.example.internproject.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.internproject.api.main.MainApiService
import com.example.internproject.api.main.response.Category
import com.example.internproject.api.main.response.Organization
import com.example.internproject.api.main.response.OrganizationalUnits
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
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

    suspend fun sendComplaint(
        managerName: String,
        complaintHeader: String,
        complaintText: String,
        orgId: Long,
        leafNodeCategoryId: Long
    ) {
        val jsonObject = JSONObject()
        jsonObject.put("categoryID", leafNodeCategoryId)
        jsonObject.put("organizationalUnitId", orgId)
        jsonObject.put("employeeName", managerName)
        jsonObject.put("title", complaintHeader)
        jsonObject.put("comment", complaintText)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        // Do the POST request and get response
        val response = mainApiService.sendRequest(requestBody)
        if (response.isSuccessful) {
        } else {

        }


    }


}