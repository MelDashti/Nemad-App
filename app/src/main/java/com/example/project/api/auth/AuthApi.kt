package com.example.project.api.auth

import com.example.project.api.auth.responses.AuthenticationResult
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


// api for authentication

interface AuthenticationApiService {
//    @POST("Authenticate/LoginAppUser")
//    suspend fun loginUser(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): AuthenticationResult

    @POST("Authenticate/LoginAppUser")
    suspend fun loginUser(@Body requestBody: RequestBody): Response<AuthenticationResult>

//    @POST("Authenticate/registerAppUser")
//    suspend fun createUser(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): AuthenticationResult

    @POST("Authenticate/registerAppUser")
    suspend fun createUser(@Body requestBody: RequestBody): Response<AuthenticationResult>


}