package com.example.project.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.project.api.auth.AuthenticationApiService
import com.example.project.api.auth.responses.AuthenticationResult
import com.example.project.api.auth.responses.RememberPassResult
import com.example.project.api.main.response.UserInfo
import com.example.project.util.PreferenceKeys
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import javax.inject.Inject
import retrofit2.Response
import kotlin.math.log


class AuthRepository @Inject constructor(
    private val authenticationApiService: AuthenticationApiService,
    private val sharedPreferences: SharedPreferences
) {


    suspend fun getUserInfo(): UserInfo {
        return authenticationApiService.getUserInfo()
    }


    suspend fun setPassword(currentPassword: String, newPassword: String) {
        val jsonObject = JSONObject()
        jsonObject.put("currentPassword", currentPassword)
        jsonObject.put("newPassword", newPassword)
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = authenticationApiService.setUserPass(requestBody)

        if (response.isSuccessful) {
            Log.d("status", "password set")
        } else Log.d("status", "failed")
    }


    suspend fun setUserName(firstName: String, lastName: String): Response<ResponseBody> {
        val jsonObject = JSONObject()
        jsonObject.put("firstName", firstName)
        jsonObject.put("lastName", lastName)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = authenticationApiService.setUserInfo(requestBody)
        if (response.isSuccessful) {
            Log.d("status", "Successful")
        } else Log.d("status", "Failed")

        return response
    }

    suspend fun setNationalId(nationalId: String): Response<ResponseBody> {
        val jsonObject = JSONObject()
        jsonObject.put("nationalId", nationalId)
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = authenticationApiService.setUserInfo(requestBody)
        return response
    }


    suspend fun verifyCode(
        username: String,
        password: String,
        verificationCode: String
    ): Response<ResponseBody> {

        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("password", password)
        jsonObject.put("verificationCode", verificationCode)
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = authenticationApiService.verify(requestBody)
        if (response.isSuccessful) {
            Log.d("status2", "Successful")
            Log.d("status2", response.message().toString())
        } else Log.d("status", "Failed")

        return response

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
        if (response.code() == 428) {
            Log.d("status", "required")
            Log.d("status", response.errorBody().toString())
        }

        if (response.isSuccessful) {
            Log.d("status", response.body()!!.message.toString())
            Log.d("status", "hello")

        } else {
            Log.e("status", response.message().toString())
            Log.d("status", response.errorBody().toString())
            Log.d("status", "noo")


        }
        return response
    }

    fun signout() {
        sharedPreferences.edit().clear().apply()
    }

    suspend fun sendPhoneNumber(phoneNo: String): Response<ResponseBody> {
        val response = authenticationApiService.rememberPassword(phoneNo)
        return response
    }

    suspend fun verifyResetPass(
        first: String,
        second: String,
        code: String
    ): Response<RememberPassResult> {
        Log.d("status2", "Successful")
        val jsonObject = JSONObject()
        jsonObject.put("username", first)
        jsonObject.put("password", second)
        jsonObject.put("verificationCode", code)
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = authenticationApiService.verifyResetPass(requestBody)
        if (response.isSuccessful) {
            Log.d("status2", "Successful")
            Log.d("status2", response.message().toString())
        } else Log.d("status", "Failed")

        return response
    }

    suspend fun resetPassword(
        value: String,
        password: String,
        token: String?
    ): Response<ResponseBody> {
        Log.d("status2", "Successful")
        val jsonObject = JSONObject()
        jsonObject.put("username", value)
        jsonObject.put("password", password)
        jsonObject.put("verificationCode", token)
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = authenticationApiService.resetPassword(requestBody)
        if (response.isSuccessful) {
            Log.d("status2", "Successful")
            Log.d("status2", response.message().toString())
        } else Log.d("status", "Failed")

        return response


    }


}