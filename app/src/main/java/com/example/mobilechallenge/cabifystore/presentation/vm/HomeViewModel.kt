package com.example.mobilechallenge.cabifystore.presentation.vm

import androidx.lifecycle.viewModelScope
import com.example.mobilechallenge.cabifystore.domain.model.onError
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.usecases.GetOffersUseCase
import com.example.mobilechallenge.cabifystore.presentation.uistate.HomeEvent
import com.example.mobilechallenge.cabifystore.presentation.uistate.HomeUiState
import com.example.mobilechallenge.common.presentation.vm.BaseViewModel
import com.example.mobilechallenge.common.ui.navigation.AppNavigator
import com.example.mobilechallenge.common.ui.navigation.Destination
import com.example.mobilechallenge.common.ui.state.ViewStateDelegate
import com.example.mobilechallenge.common.ui.state.ViewStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeScreenEvent {
    data object OfferSelected : HomeScreenEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
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

    fun onEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            when (event) {
                HomeScreenEvent.OfferSelected -> appNavigator.navigateTo(Destination.ProductsScreen.fullRoute)
            }
        }
    }

}