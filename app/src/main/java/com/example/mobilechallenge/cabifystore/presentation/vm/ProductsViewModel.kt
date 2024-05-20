package com.example.mobilechallenge.cabifystore.presentation.vm

import com.example.mobilechallenge.cabifystore.domain.model.onError
import com.example.mobilechallenge.cabifystore.domain.model.onException
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.usecases.GetProductsUseCase
import com.example.mobilechallenge.cabifystore.presentation.uistate.ProductsEvent
import com.example.mobilechallenge.cabifystore.presentation.uistate.ProductsUiState
import com.example.mobilechallenge.common.presentation.vm.BaseViewModel
import com.example.mobilechallenge.common.ui.state.ViewStateDelegate
import com.example.mobilechallenge.common.ui.state.ViewStateDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
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

}