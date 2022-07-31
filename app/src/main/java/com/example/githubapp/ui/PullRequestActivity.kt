package com.example.githubapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.ActivityPullRequestBinding
import com.example.githubapp.helper.Constants
import com.example.githubapp.helper.Constants.Companion.PULLREQUESTDATA
import com.example.githubapp.model.PullRequest
import com.example.githubapp.ui.adapter.PullRequestAdapter
import com.example.githubapp.viewmodel.PullRequestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PullRequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPullRequestBinding
    private var ownerName = ""
    private var repoName = ""
    private lateinit var pullRequestAdapter: PullRequestAdapter
    private val pullRequestViewModel: PullRequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPullRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        fetchDataFromBundle()
    }

    private val pullRequestInterface = object : PullRequestInterface {
        override fun onPullRequestClicked(pullRequest: PullRequest) {
            val pullRequestDetailActivityIntent =
                Intent(this@PullRequestActivity, PullRequestDetailActivity::class.java).apply {
                    putExtra(PULLREQUESTDATA, pullRequest)
                }

            startActivity(pullRequestDetailActivityIntent)
        }
    }

    private fun init() {
        pullRequestAdapter = PullRequestAdapter(pullRequestInterface)
        binding.pullRequestRv.adapter = pullRequestAdapter
        binding.pullRequestRv.layoutManager = LinearLayoutManager(this)
        binding.progressBar.visibility = View.VISIBLE
        binding.pullRequestRv.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun fetchDataFromBundle() {
        val bundle = intent.extras
        bundle?.let {
            ownerName = it.getString(Constants.OWNERNAME, "")
            repoName = it.getString(Constants.REPONAME, "")
        }

        lifecycleScope.launch(Dispatchers.Main) {
            val pullRequestResponseState =
                pullRequestViewModel.getClosedPullRequests(owner = ownerName, repo = repoName)
            pullRequestAdapter.submitResponseState(pullRequestResponseState)
            binding.progressBar.visibility = View.GONE
        }
    }
}