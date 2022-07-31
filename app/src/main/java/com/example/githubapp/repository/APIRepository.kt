package com.example.githubapp.repository

import com.example.githubapp.repository.retrofit.APIService
import javax.inject.Inject

class APIRepository @Inject constructor(private val apiService: APIService) {

    fun getPullRequestPagingSource(owner: String, repo: String) =
        PullRequestPagingSource(
            apiService = apiService,
            owner = owner, repo = repo
        )
}