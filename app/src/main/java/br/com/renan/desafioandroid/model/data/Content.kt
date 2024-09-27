package br.com.renan.desafioandroid.model.data

data class Content(
    val pullRequestList: List<PullRequest>?,
    val pullRequestCalculated: Pair<Int, Int>,
)
