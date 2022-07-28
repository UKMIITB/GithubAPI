package com.example.githubapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.githubapp.model.ResponseState
import com.example.githubapp.repository.APIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PullRequestViewModel @Inject constructor(private val apiRepository: APIRepository) :
    ViewModel() {

    suspend fun getClosedPullRequests(owner: String, repo: String): ResponseState {

        val responseResult = withContext(Dispatchers.IO) {
            apiRepository.getClosedPullRequests(
                owner = owner,
                repo = repo
            )
        }

        val responseBody = responseResult.body()

        return if (responseResult.code() == 200) {
            if (responseBody != null) {
                ResponseState.Success(responseBody)
            } else {
                ResponseState.Empty
            }
        } else {
            ResponseState.Error
        }
    }

}