package com.example.githubapp.model

sealed class ResponseState {
    data class Success(val pullRequestList: List<PullRequest>) : ResponseState()
    object Empty : ResponseState()
    object Error : ResponseState()
}
