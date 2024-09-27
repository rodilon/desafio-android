package br.com.renan.desafioandroid.pullrequest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.renan.desafioandroid.core.state.ResultState
import br.com.renan.desafioandroid.model.data.Content
import br.com.renan.desafioandroid.model.data.PullRequest
import br.com.renan.desafioandroid.pullrequest.repository.PullRequestRepositoryImpl
import kotlinx.coroutines.launch
import retrofit2.Response

class PullRequestViewModel(private val pullRequestRepository: PullRequestRepositoryImpl) : ViewModel() {

    private val _uiState: MutableLiveData<ResultState> = MutableLiveData(ResultState.Loading)
    val uiState: LiveData<ResultState> = _uiState

    fun getPullRequests(login: String, repoName: String) {
        viewModelScope.launch {
            val result = pullRequestRepository.requestPullRequests(login, repoName)

            when {
                result.isSuccessful -> {
                    if (result.body().isNullOrEmpty()) {
                        _uiState.value = ResultState.Empty
                    } else {
                        val content = Content(
                            result.body(),
                            calculateOpenClosedPullRequest(result.body())
                        )
                        _uiState.value = ResultState.Success(content)
                    }
                }
                else -> {
                    _uiState.value = ResultState.Error(Exception("Erro no get do pull request"))
                }
            }
        }
    }

    private fun calculateOpenClosedPullRequest(result: List<PullRequest>?): Pair<Int, Int> {

        val open = result?.filter { it.state == "open" } ?: emptyList()
        val closed = result?.filter { it.state == "closed" } ?: emptyList()

        return open.size to closed.size
    }
}