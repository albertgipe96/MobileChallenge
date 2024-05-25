package com.example.mobilechallenge.cabifystore.presentation.vm

import androidx.lifecycle.viewModelScope
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.onError
import com.example.mobilechallenge.cabifystore.domain.model.onException
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.usecases.AddProductToCartUseCase
import com.example.mobilechallenge.cabifystore.domain.usecases.GetBestOfferUseCase
import com.example.mobilechallenge.cabifystore.domain.usecases.GetProductsUseCase
import com.example.mobilechallenge.cabifystore.presentation.uistate.ProductsEvent
import com.example.mobilechallenge.cabifystore.presentation.uistate.ProductsUiState
import com.example.mobilechallenge.common.presentation.vm.BaseViewModel
import com.example.mobilechallenge.common.ui.state.ViewStateDelegate
import com.example.mobilechallenge.common.ui.state.ViewStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed class ProductsScreenEvent {
    data object DismissQuantityModal : ProductsScreenEvent()
    data class ShowQuantityModal(val product: Product) : ProductsScreenEvent()
    data class AddProductToCart(val product: Product, val quantity: Int) : ProductsScreenEvent()
    data class ComputeLowestPrice(val product: Product, val quantity: Int) : ProductsScreenEvent()
}

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getBestOfferUseCase: GetBestOfferUseCase
) : BaseViewModel(),
    ViewStateDelegate<ProductsUiState, ProductsEvent> by ViewStateDelegateImpl(ProductsUiState.Loading)
{

    init {
        withUseCaseScope {
            getProductsUseCase().resource
                .onSuccess { products ->
                    reduce { ProductsUiState.Loaded(products, null) }
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
        viewModelScope.launch {
            when (event) {
                is ProductsScreenEvent.DismissQuantityModal -> {
                    dismissQuantityModal()
                }
                is ProductsScreenEvent.ShowQuantityModal -> {
                    showQuantityModal(event.product)
                }
                is ProductsScreenEvent.AddProductToCart -> {
                    addToCart(event.product, event.quantity)
                }
                is ProductsScreenEvent.ComputeLowestPrice -> {
                    computeLowestPrice(event.product, event.quantity)
                }
            }
        }
    }

    private suspend fun dismissQuantityModal() {
        val products = (stateValue as ProductsUiState.Loaded).products
        reduce { ProductsUiState.Loaded(products, null) }
    }

    private suspend fun showQuantityModal(product: Product) {
        val products = (stateValue as ProductsUiState.Loaded).products
        reduce { ProductsUiState.Loaded(products, product) }
    }

    private fun addToCart(product: Product, quantity: Int) {
        withUseCaseScope {
            val productsToAdd = List(quantity) { product }
            addProductToCartUseCase(AddProductToCartUseCase.RequestValues(productsToAdd))
                .resource
                .onSuccess {
                    Timber.d("Product added to cart")
                    val products = (stateValue as ProductsUiState.Loaded).products
                    reduce { ProductsUiState.Loaded(products, null, null) }
                }
                .onError { message ->
                    Timber.e("Couldn't add product to cart: $message")
                }
                .onException { t ->
                    Timber.e("Couldn't add product to cart: ${t.message}")
                }
        }
    }

    private fun computeLowestPrice(product: Product, quantity: Int) {
        withUseCaseScope {
            val bestOfferPrice = getBestOfferUseCase(GetBestOfferUseCase.RequestValues(product, quantity)).price
            reduce {
                val state = (it as? ProductsUiState.Loaded)
                state?.copy(newPriceShown = bestOfferPrice) as ProductsUiState
            }
        }
    }

}