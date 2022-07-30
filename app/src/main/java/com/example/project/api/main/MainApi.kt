package com.example.project.api.main

// Api for main content
import com.example.project.api.main.response.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


// api for authentication

interface MainApiService {


    @POST("Media")
    suspend fun sendMedia(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("common/organizationalunit")
    suspend fun getOrganizationUnits(): OrganizationalUnits

    @POST("Citizen/Request")
    suspend fun sendRequest(@Body requestBody: RequestBody): Response<ComplaintResult>

    @GET("common/category")
    suspend fun getCategories(): Category

    @GET("Organization")
    suspend fun getOrganizations(): MutableList<Organization>

    @GET("Categories")
    suspend fun getCategory(id: Long): Category

    @GET("Citizen/Request")
    suspend fun getRequests(): MutableList<Requests>

}