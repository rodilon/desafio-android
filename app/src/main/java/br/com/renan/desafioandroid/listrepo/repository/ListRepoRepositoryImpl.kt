package br.com.renan.desafioandroid.listrepo.repository

import br.com.renan.desafioandroid.model.api.Api
import br.com.renan.desafioandroid.model.data.RepositoryItemsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ListRepoRepositoryImpl(private val api: Api) : ListRepoRepository {

    override suspend fun makeRequest(
        language: String,
        sort: String,
        page: Int
    ): Response<RepositoryItemsList> {
        return withContext(Dispatchers.IO) {
            api.getRepositories(language, sort, page)
        }
    }
}