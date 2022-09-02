package com.example.nemad.di

import android.content.SharedPreferences
import com.example.nemad.api.auth.AuthenticationApiService
import com.example.nemad.api.main.MainApiService
import com.example.nemad.repository.AuthRepository
import com.example.nemad.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideAuthRepository(
        sharedPreferences: SharedPreferences,
        authenticationApiService: AuthenticationApiService
    ): AuthRepository {
        return AuthRepository(authenticationApiService,sharedPreferences)
    }


    @Provides
    @ActivityRetainedScoped
    fun provideMainRepository(
        mainApiService: MainApiService,
        sharedPreferences: SharedPreferences
    ): MainRepository {
        return MainRepository(mainApiService, sharedPreferences)
    }

}





