package com.example.internproject.repository

import android.util.Log
import com.example.internproject.api.auth.AuthenticationApiService
import com.example.internproject.api.main.MainApiService
import com.example.internproject.api.main.response.CategoryResponse
import com.example.internproject.domain.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import kotlin.math.log

class MainRepository @Inject constructor(private val mainApiService: MainApiService) {

    suspend fun fetchCategories(): CategoryResponse {
        Log.d("hehehe", "before connecting to api")
        val response = mainApiService.getCategories()
        Log.d("hehehe", response.title.toString())

        return response
    }


}