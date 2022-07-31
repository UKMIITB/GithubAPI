package com.example.githubapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PullRequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPullRequestBinding
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
            pullRequestViewModel.ownerName = it.getString(Constants.OWNERNAME, "")
            pullRequestViewModel.repoName = it.getString(Constants.REPONAME, "")
        }

        lifecycleScope.launch(Dispatchers.Main) {

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pullRequestViewModel.pullRequestItems.collectLatest {
                    pullRequestAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pullRequestAdapter.loadStateFlow.collect { loadState ->

                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && pullRequestAdapter.itemCount == 0

                    binding.pullRequestEmptyContainer.visibility =
                        if (isListEmpty) View.VISIBLE else View.GONE

                    binding.pullRequestRv.visibility = if (!isListEmpty) View.VISIBLE else View.GONE

                    binding.progressBar.visibility =
                        if (loadState.source.refresh is LoadState.Loading) View.VISIBLE else View.GONE

                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error

                    errorState?.let {
                        Toast.makeText(
                            this@PullRequestActivity, "\uD83D\uDE28 Wooops ${it.error}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}