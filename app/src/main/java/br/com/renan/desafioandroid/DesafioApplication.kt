package br.com.renan.desafioandroid

import android.app.Application
import br.com.renan.desafioandroid.core.di.module.appModules
import br.com.renan.desafioandroid.provider.NetworkProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DesafioApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        startKoin {
            androidContext(this@DesafioApplication)
            modules(appModules)
        }
        NetworkProvider.init()
    }
}