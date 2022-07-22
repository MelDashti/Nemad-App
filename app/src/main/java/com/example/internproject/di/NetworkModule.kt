package com.example.internproject.di

import com.example.internproject.api.auth.AuthenticationApiService
import com.example.internproject.util.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

@Singleton
@Provides
fun provideAuthenticationApiService(retrofit: Retrofit): AuthenticationApiService{
return retrofit.create(AuthenticationApiService::class.java)
}

@Singleton
@Provides
fun provideRetrofit(moshi: Moshi,okHttpClient: OkHttpClient): Retrofit{
    val retrofit =Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).addCallAdapterFactory(
        CoroutineCallAdapterFactory()).client(okHttpClient).baseUrl(BASE_URL)
    return retrofit.build()
}









}