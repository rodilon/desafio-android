package br.com.renan.desafioandroid.core.di.module

import br.com.renan.desafioandroid.repository.RepositoryViewModel
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