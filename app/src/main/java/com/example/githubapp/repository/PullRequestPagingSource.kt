package com.example.githubapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubapp.model.PullRequest
import com.example.githubapp.repository.retrofit.APIService
import javax.inject.Inject

class PullRequestPagingSource @Inject constructor(
    private val apiService: APIService,
    private val owner: String,
    private val repo: String
) :
    PagingSource<Int, PullRequest>() {

    override fun getRefreshKey(state: PagingState<Int, PullRequest>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PullRequest> {
        val currentKey = params.key ?: 1  // default key is 1 for page = 1

        val responseResult = apiService.getClosedPullRequests(
            owner = owner,
            repo = repo,
            page = currentKey
        )

        val responseBody = responseResult.body()

        var isNextKeyPresent = false
        val linkHeader = responseResult.headers().get("link")
        linkHeader?.let {
            if (it.contains("rel=\"next\""))
                isNextKeyPresent = true
        }

        if (responseResult.code() == 200) {
            if (responseBody != null) {
                return LoadResult.Page(
                    data = responseBody,
                    prevKey = if (currentKey == 1) null else currentKey - 1,
                    nextKey = if (isNextKeyPresent) currentKey + 1 else null
                )
            } else {
                return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
            }
        } else if (responseResult.code() == 404) {
            return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
        } else {
            return LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
        }
    }
}