package com.example.project.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.project.api.main.MainApiService
import com.example.project.api.main.response.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject
import okhttp3.MultipartBody

import okhttp3.RequestBody
import org.json.JSONArray
import retrofit2.Response


class MainRepository @Inject constructor(
    private val mainApiService: MainApiService,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun fetchCategories(): Category {
        var response = mainApiService.getCategories()
        return response
    }

    suspend fun fetchRequests(): MutableList<Requests> {
        Log.d("response1", "before getting requests")
        var response = mainApiService.getRequests()
        Log.d("response1", response[0].id)
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

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        // c
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        // Do the POST request and get response
        val response = mainApiService.sendRequest(requestBody)
        if (response.isSuccessful) {
            Log.d("finally", "over")
            Log.d("finally", attachmentFilesJson.toString())

        } else {
            Log.d("finally", "not over")


        }


    }


    suspend fun sendFile(string: File): Response<MediaResponse> {

        val reqBody: RequestBody =
            RequestBody.create("multipart/form-file".toMediaTypeOrNull(), string)
        val partImage = MultipartBody.Part.createFormData("file", string.getName(), reqBody)
        val response = mainApiService.sendMedia(partImage)

        Log.d("lolaaa", response.toString())
        return response

    }


}