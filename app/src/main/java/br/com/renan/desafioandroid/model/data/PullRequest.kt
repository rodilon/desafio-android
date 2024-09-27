package br.com.renan.desafioandroid.model.data

import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("title") val title: String,
    @SerializedName("created_at") val createDate: String,
    @SerializedName("body") val body: String?,
    @SerializedName("user") val user: User,
    @SerializedName("state") val state: String,
    @SerializedName("html_url") val html_url: String,
)

data class User(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
)