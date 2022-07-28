package com.example.githubapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ItemPullRequestBinding
import com.example.githubapp.model.PullRequest
import com.example.githubapp.model.ResponseState

class PullRequestAdapter : RecyclerView.Adapter<PullRequestAdapter.PullRequestViewHolder>() {

    private var pullRequestList: List<PullRequest> = emptyList()

    class PullRequestViewHolder(private val binding: ItemPullRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(pullRequest: PullRequest) {
            binding.pullRequestTitleTv.text = pullRequest.title
            binding.closedDateTv.text = pullRequest.closed_at
            binding.createdDateTv.text = pullRequest.created_at
            binding.userNameTv.text = pullRequest.user.name
            Glide.with(binding.userProfileIv.context)
                .load(pullRequest.user.avatar_url)
                .circleCrop()
                .into(binding.userProfileIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val binding =
            ItemPullRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PullRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bindData(pullRequestList[position])
    }

    override fun getItemCount(): Int {
        return pullRequestList.size
    }

    fun submitResponseState(responseState: ResponseState) {
        handleUIBasedOnResponseState(responseState)
    }

    private fun handleUIBasedOnResponseState(responseState: ResponseState) {
        if (responseState is ResponseState.Success) {
            pullRequestList = responseState.pullRequestList
            notifyDataSetChanged()
        } else if (responseState is ResponseState.Empty) {
            pullRequestList = emptyList() // show no result
        } else {
            pullRequestList = emptyList() // show error state
        }
    }
}