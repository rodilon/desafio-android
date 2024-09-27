package br.com.renan.desafioandroid.listrepo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.renan.desafioandroid.core.state.ResultState
import br.com.renan.desafioandroid.listrepo.repository.ListRepoRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RepositoryViewModel(
    private val listRepoRepository: ListRepoRepositoryImpl
) : ViewModel() {

    private val _uiState: MutableLiveData<ResultState> = MutableLiveData(ResultState.Loading)
    val uiState: LiveData<ResultState> = _uiState

    fun getRepository(page: Int) {
        viewModelScope.launch(Dispatchers.Main) {

            val result = listRepoRepository.makeRequest("language:Java", "stars", page)

            if (result.isSuccessful) {
                _uiState.value = ResultState.Success(result.body()?.repositoryItemsList)
            } else {
                _uiState.value = ResultState.Error(Exception("Falha na comunicacao"))
            }
        }
    }
}