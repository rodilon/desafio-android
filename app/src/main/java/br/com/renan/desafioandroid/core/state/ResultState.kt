package br.com.renan.desafioandroid.core.state

sealed class ResultState {
    data class Success<out T>(val data: T) : ResultState()
    data class Error(val exception: Exception) : ResultState()
    object Loading : ResultState()
    object Empty : ResultState()
}
