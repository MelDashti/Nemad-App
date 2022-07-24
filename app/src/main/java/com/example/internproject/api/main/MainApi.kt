package com.example.internproject.api.main

// Api for main content
import com.example.internproject.api.main.response.Category
import com.example.internproject.api.main.response.Organization
import retrofit2.http.GET


// api for authentication

interface MainApiService {
    @GET("Categories")
    suspend fun getCategories(): Category

    @GET("Organization")
    suspend fun getOrganizations(): MutableList<Organization>

    @GET("Categories")
    suspend fun getCategory(id: Long): Category

}