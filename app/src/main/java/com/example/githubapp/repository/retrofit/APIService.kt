package com.example.githubapp.repository.retrofit

import com.example.githubapp.model.PullRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("repos/{owner}/{repo}/pulls")
    suspend fun getClosedPullRequests(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String = "closed",
        @Query("page") page: Int = 1
    ): Response<List<PullRequest>>
}