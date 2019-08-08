package br.com.renan.desafioandroid

import br.com.renan.desafioandroid.model.service.RepositoryService
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<RepositoryService> {
        RepositoryService()
    }

    viewModel<RepositoryViewModel> {
        RepositoryViewModel(get())
    }
}