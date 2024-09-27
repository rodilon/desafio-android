package br.com.renan.desafioandroid.model.api

import br.com.renan.desafioandroid.model.data.PullRequest
import br.com.renan.desafioandroid.model.data.RepositoryItemsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/repositories?")
    suspend fun getRepositories(
        @Query("q") language: String,
        @Query("sort") stars: String,
        @Query("page") page: Int,
    ): Response<RepositoryItemsList>

    @GET("repos/{login}/{name}/pulls?state=all")
    suspend fun getPullRequests(
        @Path(value = "login") login: String,
        @Path(value = "name") name: String,
    ): Response<List<PullRequest>>
}