package br.com.renan.desafioandroid.pullrequest.repository

import br.com.renan.desafioandroid.model.api.Api
import br.com.renan.desafioandroid.model.data.PullRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PullRequestRepositoryImpl(private val api: Api) : PullRequestRepository {
    override suspend fun requestPullRequests(
        login: String,
        repoName: String
    ): Response<List<PullRequest>> {
        return withContext(Dispatchers.IO) {
            api.getPullRequests(login, repoName)
        }
    }
}