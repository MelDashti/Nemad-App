package com.example.project.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.project.api.auth.AuthenticationApiService
import com.example.project.api.auth.responses.AuthenticationResult
import com.example.project.api.main.response.UserInfo
import com.example.project.util.PreferenceKeys
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject
import retrofit2.Response


class AuthRepository @Inject constructor(
    private val authenticationApiService: AuthenticationApiService,
    private val sharedPreferences: SharedPreferences
) {


    suspend fun getUserInfo(): UserInfo {
        return authenticationApiService.getUserInfo()
    }


    suspend fun verifyCode() {


    }


    suspend fun login(username: String, password: String): Response<AuthenticationResult> {
        // Create JSON using JSONObject

        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("password", password)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = authenticationApiService.loginUser(requestBody)
        if (response.isSuccessful) {
            val token = response.body()!!.token
            sharedPreferences.edit().putString(PreferenceKeys.PREFERENCE_AUTH_KEY, token)
                .apply()
        } else {
            Log.e("response", response.message().toString())
        }
        return response
    }


    suspend fun register(username: String, password: String): Response<AuthenticationResult> {
        Log.d("status", "register")
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("password", password)
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = authenticationApiService.createUser(requestBody)
        if (response.isSuccessful) {
            Log.d("status", response.message())

        } else {
            Log.e("status", response.message().toString())
        }
        return response
    }

    fun signout() {
        sharedPreferences.edit().clear().apply()
    }


}