package com.example.githubapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.databinding.ItemPullRequestDefaultBinding
import com.example.githubapp.databinding.ItemPullRequestEmptyBinding
import com.example.githubapp.databinding.ItemPullRequestErrorBinding
import com.example.githubapp.databinding.ItemPullRequestSuccessBinding
import com.example.githubapp.helper.DateTimeUtils
import com.example.githubapp.model.PullRequest
import com.example.githubapp.model.ResponseState
import com.example.githubapp.ui.PullRequestInterface

class PullRequestAdapter(private val pullRequestInterface: PullRequestInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var responseState: ResponseState = ResponseState.Default

    // Based on Response State class
    private val RESPONSE_STATE_DEFAULT = 0;
    private val RESPONSE_STATE_SUCCESS = 1;
    private val RESPONSE_STATE_EMPTY = 2;
    private val RESPONSE_STATE_ERROR = 3;


    inner class PullRequestSuccessViewHolder(
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
            if (adapterPosition != RecyclerView.NO_POSITION) {
                pullRequestInterface.onPullRequestClicked((responseState as ResponseState.Success).pullRequestList[adapterPosition])
            }
        }
    }

    inner class PullRequestEmptyViewHolder(private val binding: ItemPullRequestEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData() {
        }
    }

    inner class PullRequestErrorViewHolder(private val binding: ItemPullRequestErrorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData() {
        }
    }

    inner class PullRequestDefaultViewHolder(private val binding: ItemPullRequestDefaultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData() {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            RESPONSE_STATE_SUCCESS -> PullRequestSuccessViewHolder(
                ItemPullRequestSuccessBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), pullRequestInterface = pullRequestInterface
            )

            RESPONSE_STATE_EMPTY -> PullRequestEmptyViewHolder(
                ItemPullRequestEmptyBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            RESPONSE_STATE_ERROR -> PullRequestErrorViewHolder(
                ItemPullRequestErrorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )

            else -> PullRequestDefaultViewHolder(
                ItemPullRequestDefaultBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            RESPONSE_STATE_SUCCESS -> (holder as PullRequestSuccessViewHolder).bindData(
                (responseState as ResponseState.Success).pullRequestList[position]
            )

            RESPONSE_STATE_EMPTY -> (holder as PullRequestEmptyViewHolder).bindData()

            RESPONSE_STATE_ERROR -> (holder as PullRequestErrorViewHolder).bindData()

            else -> (holder as PullRequestDefaultViewHolder).bindData()
        }
    }

    override fun getItemCount(): Int {
        return if (responseState is ResponseState.Success) {
            (responseState as ResponseState.Success).pullRequestList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (responseState is ResponseState.Success) {
            RESPONSE_STATE_SUCCESS
        } else if (responseState is ResponseState.Empty) {
            RESPONSE_STATE_EMPTY
        } else if (responseState is ResponseState.Error) {
            RESPONSE_STATE_ERROR
        } else
            RESPONSE_STATE_DEFAULT
    }

    fun submitResponseState(responseState: ResponseState) {
        this.responseState = responseState;
        notifyDataSetChanged()
    }
}