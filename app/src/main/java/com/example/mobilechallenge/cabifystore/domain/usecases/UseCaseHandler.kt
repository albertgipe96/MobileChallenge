package com.example.mobilechallenge.cabifystore.domain.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
    Manage use case requests from that unique place.
 **/
interface UseCaseHandler {
    val useCaseCoroutineScope: CoroutineScope
    fun withUseCaseScope(
        loadingUpdater: ((Boolean) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        block: (suspend () -> Unit)
    ) {
        useCaseCoroutineScope.launch {
            loadingUpdater?.invoke(true)
            try {
                block()
            } catch (e: Exception) {
                onError?.invoke(e)
            } finally {
                loadingUpdater?.invoke(false)
                onComplete?.invoke()
            }
        }
    }
}