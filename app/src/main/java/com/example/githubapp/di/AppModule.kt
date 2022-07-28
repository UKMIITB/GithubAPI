package com.example.githubapp.di

import com.example.githubapp.helper.Endpoints
import com.example.githubapp.repository.retrofit.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)
}