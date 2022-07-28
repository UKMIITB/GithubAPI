package com.example.githubapp.repository

import com.example.githubapp.model.PullRequest
import com.example.githubapp.repository.retrofit.APIService
import javax.inject.Inject

class APIRepository @Inject constructor(private val apiService: APIService) {

    suspend fun getClosedPullRequests(owner: String, repo: String): List<PullRequest> {
        val responseResult = apiService.getClosedPullRequests(
            owner = owner,
            repo = repo
        )
        return responseResult.body()?.let {
            return@let it
        } ?: kotlin.run {
            return emptyList()
        }
    }
}