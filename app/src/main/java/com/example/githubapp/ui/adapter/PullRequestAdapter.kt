package com.example.githubapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ItemPullRequestSuccessBinding
import com.example.githubapp.helper.DateTimeUtils
import com.example.githubapp.model.PullRequest
import com.example.githubapp.ui.PullRequestInterface

class PullRequestAdapter(private val pullRequestInterface: PullRequestInterface) :
    PagingDataAdapter<PullRequest, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PullRequest> =
            object : DiffUtil.ItemCallback<PullRequest>() {
                override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: PullRequest,
                    newItem: PullRequest
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PullRequestViewHolder(
            ItemPullRequestSuccessBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), pullRequestInterface = pullRequestInterface
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as PullRequestViewHolder).bindData(it)
        }
    }

    inner class PullRequestViewHolder(
        private val binding: ItemPullRequestSuccessBinding,
        private val pullRequestInterface: PullRequestInterface
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData(pullRequest: PullRequest) {
            binding.pullRequestTitleTv.text = pullRequest.title
            binding.closedDateTv.text =
                DateTimeUtils.getLocalDateStringFromTimeZoneString(pullRequest.closed_at)
            binding.createdDateTv.text =
                DateTimeUtils.getLocalDateStringFromTimeZoneString(pullRequest.created_at)
            binding.userNameTv.text = pullRequest.user.name
            Glide.with(binding.userProfileIv.context)
                .load(pullRequest.user.avatar_url)
                .circleCrop()
                .into(binding.userProfileIv)
        }

        override fun onClick(v: View?) {
            getItem(absoluteAdapterPosition)?.let {
                pullRequestInterface.onPullRequestClicked(it)
            }
        }
    }
}