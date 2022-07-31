package com.example.githubapp.ui

import com.example.githubapp.model.PullRequest

interface PullRequestInterface {
    fun onPullRequestClicked(pullRequest: PullRequest)
}