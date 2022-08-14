package com.example.project.api.auth

import com.example.project.api.auth.responses.AuthenticationResult
import com.example.project.api.auth.responses.RememberPassResult
import com.example.project.api.main.response.UserInfo
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


// api for authentication

interface AuthenticationApiService {

    @GET("Authenticate/ForgotPasswordAppUser")
    suspend fun rememberPassword(@Query("username") username: String): Response<ResponseBody>

    @POST("Authenticate/ForgotPassword")
    suspend fun verifyResetPass(@Body requestBody: RequestBody): Response<RememberPassResult>

    @POST("Authenticate/Verify")
    suspend fun verify(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("Authenticate/Me")
    suspend fun getUserInfo(): UserInfo

    @POST("Authenticate/LoginAppUser")
    suspend fun loginUser(@Body requestBody: RequestBody): Response<AuthenticationResult>

    @POST("Authenticate/registerAppUser")
    suspend fun createUser(@Body requestBody: RequestBody): Response<AuthenticationResult>


    @PUT("Authenticate/Me")
    suspend fun setUserInfo(@Body requestBody: RequestBody): Response<ResponseBody>

    @PUT("Authenticate/Password")
    suspend fun setUserPass(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("Authenticate/ResetPassword")
    suspend fun resetPassword(@Body requestBody: RequestBody): Response<ResponseBody>

}