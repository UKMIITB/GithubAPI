package com.example.githubapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.helper.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.searchBt.setOnClickListener {
            onSearchButtonClicked()
        }
    }

    private fun onSearchButtonClicked() {
        val validate = validateInputFields()
        if (!validate) {
            Toast.makeText(this, "Please enter both repo & user", Toast.LENGTH_SHORT).show()
            return
        }

        val bundle = Bundle()
        bundle.putString(Constants.OWNERNAME, binding.ownerEt.text.toString())
        bundle.putString(Constants.REPONAME, binding.repoEt.text.toString())
        val pullRequestActivityIntent = Intent(this, PullRequestActivity::class.java)
        pullRequestActivityIntent.putExtras(bundle)
        startActivity(pullRequestActivityIntent)
    }

    private fun validateInputFields(): Boolean {
        if (!binding.ownerEt.text.isNullOrEmpty() && !binding.repoEt.text.isNullOrEmpty())
            return true
        return false
    }
}