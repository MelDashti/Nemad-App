package com.example.internproject.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.internproject.api.main.MainApiService
import com.example.internproject.api.main.response.Category
import com.example.internproject.api.main.response.Organization
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApiService: MainApiService) {



    suspend fun fetchCategories(): Category {
        Log.d("hehehe", "before connecting to api")
        var response = mainApiService.getCategories()
        Log.d("hehehe", response.title.toString())
        Log.d("hehehe", response.children!![0].title.toString())
        Log.d("hehehe", response.children!![1].children!![0].title.toString())
        return response
    }

    suspend fun fetchOrganization(): MutableList<Organization> {
        Log.d("hehehe", "before connecting to api")
        var response = mainApiService.getOrganizations()
        return response
    }

    suspend fun getCategory(id: Long) {
        var response = mainApiService.getCategories()


    }


}