package com.example.mobilechallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilechallenge.cabifystore.domain.model.CartProduct
import com.example.mobilechallenge.cabifystore.domain.model.onError
import com.example.mobilechallenge.cabifystore.domain.model.onException
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.model.toProduct
import com.example.mobilechallenge.cabifystore.domain.usecases.AddNewPurchaseUseCase
import com.example.mobilechallenge.cabifystore.domain.usecases.ComputeTotalPriceUseCase
import com.example.mobilechallenge.cabifystore.domain.usecases.EmptyCartUseCase
import com.example.mobilechallenge.cabifystore.domain.usecases.GetBestOfferUseCase
import com.example.mobilechallenge.cabifystore.domain.usecases.GetCartProductsUseCase
import com.example.mobilechallenge.common.ui.navigation.AppNavigator
import com.example.mobilechallenge.common.ui.navigation.NavigationIntent
import com.example.mobilechallenge.common.ui.state.ViewStateDelegate
import com.example.mobilechallenge.common.ui.state.ViewStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@Immutable
sealed class MainUiState {
    data object Idle : MainUiState()
    data class ShowCart(val cartProducts: List<CartProduct>, val totalPrice: Double) : MainUiState()
}

sealed interface MainEvent {

}

sealed class MainScreenEvent {
    data object DismissModal : MainScreenEvent()
    data object ShowCartModal : MainScreenEvent()
    data class PurchaseCart(val totalPrice: Double) : MainScreenEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    val appNavigator: AppNavigator,
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val computeTotalPriceUseCase: ComputeTotalPriceUseCase,
    private val addNewPurchaseUseCase: AddNewPurchaseUseCase,
    private val emptyCartUseCase: EmptyCartUseCase
) : ViewModel(), ViewStateDelegate<MainUiState, MainEvent> by ViewStateDelegateImpl(MainUiState.Idle) {

    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            appNavigator.setCustomNavigationChannel(Channel<NavigationIntent>(capacity = Int.MAX_VALUE, onBufferOverflow = BufferOverflow.DROP_LATEST))
            delay(300)
            splashShowFlow.emit(false)
        }
    }

    fun onEvent(event: MainScreenEvent) {
        viewModelScope.launch {
            when (event) {
                MainScreenEvent.DismissModal -> {
                    reduce { MainUiState.Idle }
                }
                MainScreenEvent.ShowCartModal -> {
                    getCartProductsUseCase().resource
                        .onSuccess { cartProducts ->
                            val totalPrice = computeTotalPriceUseCase(ComputeTotalPriceUseCase.RequestValues(cartProducts)).totalPrice
                            reduce { MainUiState.ShowCart(cartProducts, totalPrice) }
                        }
                        .onError { message ->
                            Timber.e("Error loading cart products: $message")
                        }
                        .onException { t ->
                            Timber.e("Error loading cart products: ${t.message}")
                        }

                }
                is MainScreenEvent.PurchaseCart -> {
                    addNewPurchaseUseCase(AddNewPurchaseUseCase.RequestValues(event.totalPrice)).resource
                        .onSuccess {
                            emptyCartUseCase().resource
                                .onSuccess {
                                    onEvent(MainScreenEvent.DismissModal)
                                }
                        }
                        .onError { message ->
                            Timber.e("Error purchasing cart products: $message")
                        }
                }
            }
        }
    }

}