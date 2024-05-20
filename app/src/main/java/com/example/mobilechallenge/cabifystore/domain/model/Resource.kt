package com.example.mobilechallenge.cabifystore.domain.model

sealed class Resource<T>() {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val errorMessage: String?) : Resource<T>()
    class Exception<T>(val t: Throwable) : Resource<T>()
}

suspend fun <T: Any> Resource<T>.onSuccess(
    executable: suspend (T) -> Unit
): Resource<T> = apply {
    if (this is Resource.Success<T>) {
        executable(data)
    }
}

suspend fun <T: Any> Resource<T>.onError(
    executable: suspend (errorMessage: String?) -> Unit
): Resource<T> = apply {
    if (this is Resource.Error<T>) {
        executable(errorMessage)
    }
}

suspend fun <T: Any> Resource<T>.onException(
    executable: suspend (t: Throwable) -> Unit
): Resource<T> = apply {
    if (this is Resource.Exception<T>) {
        executable(t)
    }
}