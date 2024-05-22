package com.example.mobilechallenge.cabifystore.presentation.vm

import androidx.lifecycle.viewModelScope
import com.example.mobilechallenge.cabifystore.domain.model.onError
import com.example.mobilechallenge.cabifystore.domain.model.onException
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.usecases.GetPurchasesUseCase
import com.example.mobilechallenge.cabifystore.presentation.uistate.PurchasesEvent
import com.example.mobilechallenge.cabifystore.presentation.uistate.PurchasesUiState
import com.example.mobilechallenge.common.presentation.vm.BaseViewModel
import com.example.mobilechallenge.common.ui.state.ViewStateDelegate
import com.example.mobilechallenge.common.ui.state.ViewStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasesViewModel @Inject constructor(
    private val getPurchasesUseCase: GetPurchasesUseCase
) : BaseViewModel(),
    ViewStateDelegate<PurchasesUiState, PurchasesEvent> by ViewStateDelegateImpl(PurchasesUiState.Loading)
{

    init {
        viewModelScope.launch {
            getPurchasesUseCase().resource
                .onSuccess { purchases ->
                    reduce { PurchasesUiState.Loaded(purchases) }
                }
                .onError { message ->
                    reduce { PurchasesUiState.Error(message ?: "Error") }
                }
                .onException { t ->
                    reduce { PurchasesUiState.Error(t.message ?: "Error") }
                }
        }
    }

}