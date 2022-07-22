package com.example.internproject.repository

import com.example.internproject.api.auth.AuthenticationApiService
import com.example.internproject.api.auth.responses.AuthenticationResult
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authenticationApiService: AuthenticationApiService) {

    suspend fun login(email: String, password: String): AuthenticationResult {
        val result = authenticationApiService.loginUser(email, password)
        return result
    }

}