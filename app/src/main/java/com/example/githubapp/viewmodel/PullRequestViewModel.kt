package com.example.githubapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubapp.model.PullRequest
import com.example.githubapp.repository.APIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PullRequestViewModel @Inject constructor(private val apiRepository: APIRepository) :
    ViewModel() {

    var ownerName = ""
    var repoName = ""

    val pullRequestItems: Flow<PagingData<PullRequest>> = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            apiRepository.getPullRequestPagingSource(
                owner = ownerName,
                repo = repoName
            )
        }
    ).flow
        .cachedIn(viewModelScope)
}