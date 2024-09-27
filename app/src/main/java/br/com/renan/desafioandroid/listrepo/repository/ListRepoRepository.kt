package br.com.renan.desafioandroid.listrepo.repository

import br.com.renan.desafioandroid.model.data.RepositoryItemsList
import retrofit2.Response

interface ListRepoRepository {

    suspend fun makeRequest(
        language: String,
        sort: String,
        page: Int
    ): Response<RepositoryItemsList>
}
