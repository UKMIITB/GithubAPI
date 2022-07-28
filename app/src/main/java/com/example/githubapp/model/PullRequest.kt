package com.example.githubapp.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("closed_at")
    val closed_at: String,
    @SerializedName("user")
    val user: User
)
