package com.example.githubapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.ActivityPullRequestBinding
import com.example.githubapp.helper.Constants
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

    private fun init() {
        pullRequestAdapter = PullRequestAdapter()
        binding.pullRequestRv.adapter = pullRequestAdapter
        binding.pullRequestRv.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchDataFromBundle() {
        val bundle = intent.extras
        bundle?.let {
            ownerName = it.getString(Constants.OWNERNAME, "")
            repoName = it.getString(Constants.REPONAME, "")
        }

        lifecycleScope.launch(Dispatchers.Main) {
            val pullRequestList =
                pullRequestViewModel.getClosedPullRequests(owner = ownerName, repo = repoName)
            pullRequestAdapter.submitList(pullRequestList)
        }
    }
}