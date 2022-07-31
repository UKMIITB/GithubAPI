package com.example.githubapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val avatar_url: String,
) : Parcelable
