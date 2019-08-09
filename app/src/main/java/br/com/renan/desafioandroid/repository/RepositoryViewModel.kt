package br.com.renan.desafioandroid.repository

import br.com.renan.desafioandroid.core.base.BaseViewModel
import br.com.renan.desafioandroid.model.data.RepositoryItemsList
import br.com.renan.desafioandroid.model.service.RepositoryService
import br.com.renan.desafioandroid.core.helper.ResourceLiveData
import br.com.renan.desafioandroid.core.helper.toNetworkFlowable

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