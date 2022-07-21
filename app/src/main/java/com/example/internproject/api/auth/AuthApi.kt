package com.example.internproject.api.auth
import com.example.internproject.api.auth.responses.AuthenticationResult
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterApiService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginCustomer(
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthenticationResult

    @FormUrlEncoded
    @POST("register.php")
    suspend fun createCustomer(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthenticationResult


}