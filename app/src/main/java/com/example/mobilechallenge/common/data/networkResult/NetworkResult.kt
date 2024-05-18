package com.example.mobilechallenge.common.data.networkResult

import retrofit2.HttpException
import retrofit2.Response

sealed class NetworkResult<T: Any> {
    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class Error<T: Any>(val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}

fun <T: Any> handleApi(
    response: Response<T>
): NetworkResult<T> {
    return try {
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(data = body)
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        NetworkResult.Exception(e = e)
    }
}

fun <T: Any> NetworkResult<T>.onSuccess(
    executable: (T) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Success<T>) {
        executable(data)
    }
}

fun <T: Any> NetworkResult<T>.onError(
    executable: (code: Int, message: String?) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Error<T>) {
        executable(code, message)
    }
}

fun <T: Any> NetworkResult<T>.onException(
    executable: (e: Throwable) -> Unit
): NetworkResult<T> = apply {
    if (this is NetworkResult.Exception<T>) {
        executable(e)
    }
}