package com.example.mobilechallenge.cabifystore.presentation.uistate

import com.example.mobilechallenge.cabifystore.domain.model.Product
import javax.annotation.concurrent.Immutable

@Immutable
sealed class ProductsUiState {
    data object Loading : ProductsUiState()
    data class Error(val message: String) : ProductsUiState()
    data class Loaded(val products: List<Product>, val showingProductModal: Product?) : ProductsUiState()
}

sealed interface ProductsEvent {

}