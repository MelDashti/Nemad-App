package com.example.internproject.api.main

// Api for main content
import com.example.internproject.api.auth.responses.AuthenticationResult
import com.example.internproject.api.main.response.CategoryResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


// api for authentication

interface MainApiService {
    @GET("Categories")
    suspend fun getCategories(): CategoryResponse
}