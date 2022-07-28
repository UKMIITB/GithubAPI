package com.example.githubapp.repository

import com.example.githubapp.repository.retrofit.APIService
import javax.inject.Inject

class APIRepository @Inject constructor(private val apiService: APIService) {

    suspend fun getClosedPullRequests() = apiService.getClosedPullRequests()
}