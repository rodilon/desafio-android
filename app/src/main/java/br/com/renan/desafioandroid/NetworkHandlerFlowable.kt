package br.com.renan.desafioandroid

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Flowable
import java.io.IOException

fun <T> Flowable<T>.toNetworkFlowable() = NetworkHandlerFlowable(this)

data class NetworkHandlerFlowable<T>(val observable: Flowable<T>) {

    fun subscribeLiveData(viewModel: BaseViewModel, liveData: ResourceLiveData<T>) {
        viewModel.disposables.add(
            observable
                .doOnError { viewModel.loading.postValue(false) }
                .subscribe(
                    { liveData.postValue(Resource.success(it))},
                    { handleError(liveData, it) })
        )
    }

    private fun handleError(liveData: MutableLiveData<Resource<T>>, it: Throwable) {
        if (it is IOException) {
            liveData.postValue(Resource.error(it, Status.INTERNET))
        } else {
            liveData.postValue(Resource.error(it))
        }
    }
}