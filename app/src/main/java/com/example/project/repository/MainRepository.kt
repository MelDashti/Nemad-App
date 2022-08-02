package com.example.project.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.project.api.main.MainApiService
import com.example.project.api.main.response.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONArray
import retrofit2.Response

class MainRepository @Inject constructor(
    private val mainApiService: MainApiService,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun fetchCategories(): Category {
        return mainApiService.getCategories()
    }

    suspend fun fetchRequests(): MutableList<Requests> {
        return mainApiService.getRequests()
    }


    suspend fun fetchOrganizationalUnits(): OrganizationalUnits {
        return mainApiService.getOrganizationUnits()
    }

    suspend fun fetchOrganization(): MutableList<Organization> {
        return mainApiService.getOrganizations()
    }

    suspend fun sendComplaint(
        managerName: String,
        complaintHeader: String,
        complaintText: String,
        orgId: Long,
        leafNodeCategoryId: Long,
        attachmentFiles: List<String>
    ) {
        val jsonObject = JSONObject()

        val attachmentFilesJson = JSONArray()
        attachmentFiles.forEach {
            attachmentFilesJson.put(it)
        }

        jsonObject.put("categoryID", leafNodeCategoryId)
        jsonObject.put("organizationalUnitId", orgId)
        jsonObject.put("employeeName", managerName)
        jsonObject.put("title", complaintHeader)
        jsonObject.put("comment", complaintText)
        jsonObject.put("attachmentFiles", attachmentFilesJson)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = mainApiService.sendRequest(requestBody)
        if (response.isSuccessful) {
            Log.d("response", "Successful")

        } else {
            Log.d("response", response.errorBody().toString())


        }


    }


    suspend fun sendFile(string: File): Response<MediaResponse> {

        val reqBody: RequestBody =
            string.asRequestBody("multipart/form-file".toMediaTypeOrNull())
        val partImage = MultipartBody.Part.createFormData("file", string.name, reqBody)
        return mainApiService.sendMedia(partImage)

    }


}