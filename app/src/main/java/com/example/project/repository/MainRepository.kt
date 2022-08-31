package com.example.project.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.project.adapter.PassengersDataSource
import com.example.project.api.main.MainApiService
import com.example.project.api.main.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Response

class MainRepository @Inject constructor(
    private val mainApiService: MainApiService,
    private val sharedPreferences: SharedPreferences
) {

    var list: List<Category>? = listOf()

    suspend fun fetchCategories(): Category {
        val category = mainApiService.getCategories()
        list = category.children
        return category
    }

    suspend fun fetchRequests(): MutableList<Requests> {
        return mainApiService.getRequests()
    }

    suspend fun fetchRecentRequests(): MutableList<Requests> {
        return mainApiService.getRequestsDesc()
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
    ): Response<ComplaintResult> {
        val jsonObject = JSONObject()

        val attachmentFilesJson = JSONArray()
        attachmentFiles.forEach {
            attachmentFilesJson.put(it)
        }

        jsonObject.put("categoryID", leafNodeCategoryId)
        jsonObject.put("organizationalUnitId", orgId)
        jsonObject.put("employeeName", managerName)
        jsonObject.put("title", complaintHeader)
        jsonObject.put("comments", complaintText)
        jsonObject.put("attachmentFiles", attachmentFilesJson)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        Log.d("request1123", requestBody.toString())
        val response = mainApiService.sendRequest(requestBody)
        if (response.isSuccessful) {
            Log.d("response", "Successful")
        } else {
            Log.d("response", response.errorBody().toString())

        }
        return response


    }


    suspend fun sendFile(string: File): Response<MediaResponse> {

        val reqBody: RequestBody =
            string.asRequestBody("multipart/form-file".toMediaTypeOrNull())
        val partImage = MultipartBody.Part.createFormData("file", string.name, reqBody)
        return mainApiService.sendMedia(partImage)

    }

    fun fetchPagedReq(viewModelScope: CoroutineScope): Flow<PagingData<Requests>> {
        return Pager(PagingConfig(pageSize = 10)) {
            PassengersDataSource(mainApiService)
        }.flow.cachedIn(viewModelScope)

    }

    suspend fun fetchCurrentReq(id: String): Requests? {
        return mainApiService.getCurrentReq(id)
    }

    suspend fun sendRating(reqId: String, rating: Long): Response<ResponseBody> {
        val jsonObject = JSONObject()
        jsonObject.put("rating", rating)
        jsonObject.put("isConfirmed", true)
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = mainApiService.setRating(reqId, requestBody)
        if (response.isSuccessful) {
            Log.d("status", "rating Send")
        } else Log.d("status", "failed")
        return response

    }

    suspend fun fetchCurrentOrganizationalUnits(parentId: Long?): OrganizationalUnits {
        return mainApiService.getCurrentOrganizationUnits(parentId)
    }




}