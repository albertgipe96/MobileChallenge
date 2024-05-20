package com.example.mobilechallenge.cabifystore.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilechallenge.cabifystore.presentation.uistate.ProductsUiState
import com.example.mobilechallenge.cabifystore.presentation.vm.ProductsViewModel
import com.example.mobilechallenge.common.ui.state.collectWithLifecycle

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel<ProductsViewModel>()
) {
    val uiState by viewModel.collectWithLifecycle()

    Column {
        when (val state = uiState) {
            ProductsUiState.Loading -> {
                CircularProgressIndicator()
            }
            is ProductsUiState.Error -> {
                Text(text = state.message)
            }
            is ProductsUiState.Loaded -> {
                state.products.forEach {
                    Text(text = "${it.code} - ${it.name} - ${it.price}€")
                }
            }
        }
    }

}