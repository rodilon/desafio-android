package br.com.renan.desafioandroid

import br.com.renan.desafioandroid.model.data.RepositoryItemsList
import br.com.renan.desafioandroid.model.service.RepositoryService

class RepositoryViewModel(
    private val repositoryService: RepositoryService
) : BaseViewModel(){

    val repositoryResult = ResourceLiveData<RepositoryItemsList>()

    fun getRepositoryData(page: Int) {
        repositoryService.getData("language:Java", "stars", page)
            .doFinally { loading.postValue(false) }
            .toNetworkFlowable()
            .subscribeLiveData(this, repositoryResult)
    }
}