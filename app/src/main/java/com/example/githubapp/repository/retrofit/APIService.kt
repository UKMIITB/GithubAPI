package com.example.githubapp.repository.retrofit

import com.example.githubapp.model.PullRequest
import retrofit2.http.GET

interface APIService {
    @GET("repos/{owner}/{repo}/pulls?state=closed")
    suspend fun getClosedPullRequests(): List<PullRequest>
}