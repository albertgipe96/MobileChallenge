package com.example.mobilechallenge.common.presentation.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilechallenge.cabifystore.domain.usecases.UseCaseHandler

abstract class BaseViewModel() : ViewModel(), UseCaseHandler {

    val loadingManager: LoadingManager = LoadingManager()

    override val useCaseCoroutineScope = viewModelScope

    override fun withUseCaseScope(
        loadingUpdater: ((Boolean) -> Unit)?,
        onError: ((Throwable) -> Unit)?,
        onComplete: (() -> Unit)?,
        block: suspend () -> Unit
    ) {
        val defaultLoadingUpdater = { isLoading: Boolean ->
            loadingManager.setLoading(isLoading)
        }
        super.withUseCaseScope(
            loadingUpdater = {
                loadingUpdater?.invoke(it) ?: defaultLoadingUpdater(it)
            },
            onError = {
                onError?.invoke(it)
            },
            onComplete = onComplete,
            block = block
        )
    }
}

class LoadingManager {
    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean = _isLoading.value
    fun setLoading(value: Boolean) {
        _isLoading.value = value
    }
}