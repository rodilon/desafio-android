package br.com.renan.desafioandroid.core.di.module

import br.com.renan.desafioandroid.BuildConfig
import br.com.renan.desafioandroid.core.NetworkConnectionInterceptor
import br.com.renan.desafioandroid.listrepo.repository.ListRepoRepositoryImpl
import br.com.renan.desafioandroid.listrepo.viewmodel.RepositoryViewModel
import br.com.renan.desafioandroid.model.api.Api
import br.com.renan.desafioandroid.pullrequest.repository.PullRequestRepositoryImpl
import br.com.renan.desafioandroid.pullrequest.viewmodel.PullRequestViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {

    factory { ListRepoRepositoryImpl(get()) }

    factory { PullRequestRepositoryImpl(get()) }
}

val viewModelModule = module {

    viewModel {
        RepositoryViewModel(get())
    }

    viewModel {
        PullRequestViewModel(get())
    }
}

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor())
            .addInterceptor(logging)
            .build()
    }

    single { get<Retrofit>().create(Api::class.java) }
}

val appModules = listOf(retrofitModule, viewModelModule, repositoryModule)
