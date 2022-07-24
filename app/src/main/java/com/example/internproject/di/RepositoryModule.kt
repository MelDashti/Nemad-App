package com.example.internproject.di

import android.content.SharedPreferences
import com.example.internproject.api.auth.AuthenticationApiService
import com.example.internproject.api.main.MainApiService
import com.example.internproject.repository.AuthRepository
import com.example.internproject.repository.MainRepository
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





