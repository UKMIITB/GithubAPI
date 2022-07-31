package com.example.githubapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PullRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("closed_at")
    val closed_at: String,
    @SerializedName("user")
    val user: User
) : Parcelable
