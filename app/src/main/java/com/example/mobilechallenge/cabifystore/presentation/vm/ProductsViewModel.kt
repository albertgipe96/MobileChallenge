package com.example.mobilechallenge.cabifystore.presentation.vm

import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.onError
import com.example.mobilechallenge.cabifystore.domain.model.onException
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.usecases.AddProductToCartUseCase
import com.example.mobilechallenge.cabifystore.domain.usecases.GetProductsUseCase
import com.example.mobilechallenge.cabifystore.presentation.uistate.ProductsEvent
import com.example.mobilechallenge.cabifystore.presentation.uistate.ProductsUiState
import com.example.mobilechallenge.common.presentation.vm.BaseViewModel
import com.example.mobilechallenge.common.ui.state.ViewStateDelegate
import com.example.mobilechallenge.common.ui.state.ViewStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

sealed class ProductsScreenEvent {
    data class AddProductToCart(val product: Product) : ProductsScreenEvent()
}

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : BaseViewModel(), ViewStateDelegate<ProductsUiState, ProductsEvent> by ViewStateDelegateImpl(ProductsUiState.Loading) {

    init {
        withUseCaseScope {
            getProductsUseCase().resource
                .onSuccess { products ->
                    reduce { ProductsUiState.Loaded(products) }
                }
                .onError { message ->
                    reduce { ProductsUiState.Error(message ?: "Error") }
                }
                .onException { t ->
                    reduce { ProductsUiState.Error(t.message ?: "Error") }
                }
        }
    }

    fun onEvent(event: ProductsScreenEvent) {
        when (event) {
            is ProductsScreenEvent.AddProductToCart -> {
                addToCart(event.product)
            }
        }
    }

    private fun addToCart(product: Product) {
        withUseCaseScope {
            addProductToCartUseCase(AddProductToCartUseCase.RequestValues(product))
                .resource
                .onSuccess {
                    Timber.d("Product added to cart")
                }
                .onError { message ->
                    Timber.e("Couldn't add product to cart: $message")
                }
                .onException { t ->
                    Timber.e("Couldn't add product to cart: ${t.message}")
                }
        }

    }

}