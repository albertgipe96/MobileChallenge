package com.example.mobilechallenge.cabifystore.domain.model

sealed class Resource<T>() {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val errorMessage: String?) : Resource<T>()
    class Exception<T>(val t: Throwable) : Resource<T>()
}

fun <T: Any> Resource<T>.onSuccess(
    executable: (T) -> Unit
): Resource<T> = apply {
    if (this is Resource.Success<T>) {
        executable(data)
    }
}

fun <T: Any> Resource<T>.onError(
    executable: (errorMessage: String?) -> Unit
): Resource<T> = apply {
    if (this is Resource.Error<T>) {
        executable(errorMessage)
    }
}

fun <T: Any> Resource<T>.onException(
    executable: (t: Throwable) -> Unit
): Resource<T> = apply {
    if (this is Resource.Exception<T>) {
        executable(t)
    }
}