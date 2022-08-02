package com.example.project.api.auth

import com.example.project.api.auth.responses.AuthenticationResult
import com.example.project.api.main.response.UserInfo
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


// api for authentication

interface AuthenticationApiService {

    @GET("Authenticate/Me")
    suspend fun getUserInfo(): UserInfo

    @POST("Authenticate/LoginAppUser")
    suspend fun loginUser(@Body requestBody: RequestBody): Response<AuthenticationResult>

    @POST("Authenticate/registerAppUser")
    suspend fun createUser(@Body requestBody: RequestBody): Response<AuthenticationResult>


}