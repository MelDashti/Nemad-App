package com.example.project.api.main

// Api for main content
import com.example.project.api.main.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


// api for authentication

interface MainApiService {


    @Multipart
    @POST("Media")
    suspend fun sendMedia(
        @Part file: MultipartBody.Part
    ): Response<MediaResponse>

    @GET("common/organizationalunit")
    suspend fun getOrganizationUnits(): OrganizationalUnits

    @POST("Citizen/Request")
    suspend fun sendRequest(@Body requestBody: RequestBody): Response<ComplaintResult>

    @GET("common/category")
    suspend fun getCategories(): Category

    @GET("Organization")
    suspend fun getOrganizations(): MutableList<Organization>

    @GET("Citizen/Request")
    suspend fun getRequests(): MutableList<Requests>

}