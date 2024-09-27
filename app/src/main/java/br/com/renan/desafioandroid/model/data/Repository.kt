package br.com.renan.desafioandroid.model.data

import com.google.gson.annotations.SerializedName

data class RepositoryItemsList(
    @SerializedName("items") val repositoryItemsList: List<Repository>,
)

data class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("stargazers_count") val starts: Int,
    @SerializedName("forks_count") val forks: Int,
    @SerializedName("full_name") val fullName: String,
)

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
)