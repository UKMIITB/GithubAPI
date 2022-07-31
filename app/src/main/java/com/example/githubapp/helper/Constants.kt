package com.example.githubapp.helper

class Constants {
    companion object {
        val OWNERNAME = "owner_name"
        val REPONAME = "repo_name"
        val PULLREQUESTDATA = "pull_request_data"

        val NO_REPOSITORY_EXIST_THROWABLE = Throwable("No such repository exist")
        val SERVER_ERROR_OCCURRED_THROWABLE = Throwable("Server error occurred")
    }
}