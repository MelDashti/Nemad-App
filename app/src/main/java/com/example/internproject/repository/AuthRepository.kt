package com.example.internproject.repository

import android.util.Log
import com.example.internproject.api.auth.AuthenticationApiService
import com.example.internproject.api.auth.responses.AuthenticationResult
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject
import com.google.gson.JsonObject


class AuthRepository @Inject constructor(private val authenticationApiService: AuthenticationApiService) {


    suspend fun login(username: String, password: String) {
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("password", password)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = authenticationApiService.loginUser(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Convert raw JSON to pretty JSON using GSON library
                    Log.d("status", response.body().toString())
                    Log.d("status", response.message())

                } else {

                    Log.e("status", response.code().toString())
                    Log.e("status", response.body().toString())
                    Log.e("status", response.message().toString())
                }
            }
        }
    }


    suspend fun register(username: String, password: String) {
        Log.d("status", "register")
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("password", password)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = authenticationApiService.loginUser(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Convert raw JSON to pretty JSON using GSON library
                    Log.d("status", response.body().toString())
                    Log.d("status", response.message())

                } else {

                    Log.e("status", response.code().toString())
                    Log.e("status", response.body().toString())
                    Log.e("status", response.message().toString())
                }
            }
        }
    }


}