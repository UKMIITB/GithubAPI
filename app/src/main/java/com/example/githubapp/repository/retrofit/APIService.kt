package com.example.githubapp.repository.retrofit

import com.example.githubapp.model.PullRequest
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("repos/{owner}/{repo}/pulls?state=closed")
    suspend fun getClosedPullRequests(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<PullRequest>
}