package com.example.internproject.api.main

// Api for main content
import com.example.internproject.api.main.response.Category
import com.example.internproject.api.main.response.Organization
import com.example.internproject.api.main.response.OrganizationalUnits
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST


// api for authentication

interface MainApiService {

    @GET("OrganizationalUnits")
    suspend fun getOrganizationUnits(): OrganizationalUnits

    @POST("Citizen/Request")
    suspend fun sendRequest(): Response<ResponseBody>

    @GET("Categories")
    suspend fun getCategories(): Category

    @GET("Organization")
    suspend fun getOrganizations(): MutableList<Organization>

    @GET("Categories")
    suspend fun getCategory(id: Long): Category

}