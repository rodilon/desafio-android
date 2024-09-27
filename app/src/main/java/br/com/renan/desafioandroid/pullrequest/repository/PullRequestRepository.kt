package br.com.renan.desafioandroid.pullrequest.repository

import br.com.renan.desafioandroid.model.data.PullRequest
import retrofit2.Response

interface PullRequestRepository {
    suspend fun requestPullRequests(login: String, repoName: String): Response<List<PullRequest>>
}