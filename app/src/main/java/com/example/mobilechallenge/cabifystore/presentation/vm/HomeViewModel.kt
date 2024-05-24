package com.example.mobilechallenge.cabifystore.presentation.vm

import com.example.mobilechallenge.cabifystore.domain.model.onError
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.usecases.GetOffersUseCase
import com.example.mobilechallenge.cabifystore.presentation.uistate.HomeEvent
import com.example.mobilechallenge.cabifystore.presentation.uistate.HomeUiState
import com.example.mobilechallenge.common.presentation.vm.BaseViewModel
import com.example.mobilechallenge.common.ui.state.ViewStateDelegate
import com.example.mobilechallenge.common.ui.state.ViewStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase
) : BaseViewModel(), ViewStateDelegate<HomeUiState, HomeEvent> by ViewStateDelegateImpl(HomeUiState.Loading) {

    init {
        withUseCaseScope {
            getOffersUseCase().resource
                .onSuccess { offers ->
                    reduce { HomeUiState.Loaded(offers) }
                }
                .onError { message ->
                    reduce { HomeUiState.Error(message ?: "Error") }
                }
        }
    }

}