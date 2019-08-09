package br.com.renan.desafioandroid.core.helper

enum class Status { SUCCESS, ERROR, INTERNET}

class Resource<out T> (
    val exception: Throwable?,
    val status: Status,
    val data: T?
) {
    companion object {
        fun <T> success(data: T?) =
            Resource(
                null,
                Status.SUCCESS,
                data
            )
        fun error(exception: Throwable?, status: Status = Status.ERROR) =
            Resource(exception, status, null)
    }
}


