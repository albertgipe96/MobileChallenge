package com.example.mobilechallenge.cabifystore.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilechallenge.cabifystore.presentation.components.AddProductModalContent
import com.example.mobilechallenge.cabifystore.presentation.components.ProductCard
import com.example.mobilechallenge.cabifystore.presentation.uistate.ProductsUiState
import com.example.mobilechallenge.cabifystore.presentation.vm.ProductsScreenEvent
import com.example.mobilechallenge.cabifystore.presentation.vm.ProductsViewModel
import com.example.mobilechallenge.common.ui.components.BottomModal
import com.example.mobilechallenge.common.ui.components.TopBar
import com.example.mobilechallenge.common.ui.state.collectWithLifecycle
import com.example.mobilechallenge.ui.theme.M500
import com.example.mobilechallenge.ui.utils.Spacing

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel<ProductsViewModel>()
) {
    val uiState by viewModel.collectWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(M500),
        topBar = {
            TopBar(
                title = "Products"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.MEDIUM.spacing)
                .verticalScroll(rememberScrollState())
        ) {
            when (val state = uiState) {
                ProductsUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is ProductsUiState.Error -> {
                    Text(text = state.message)
                }
                is ProductsUiState.Loaded -> {
                    state.products.forEach { product ->
                        ProductCard(
                            product = product,
                            onPurchase = { viewModel.onEvent(ProductsScreenEvent.ShowQuantityModal(product)) }
                        )
                        Spacer(modifier = Modifier.height(Spacing.MEDIUM.spacing))
                    }
                }
            }
        }

        // Bottom Sheet section starts ---------------------------------------------------------
        if (uiState is ProductsUiState.Loaded && (uiState as? ProductsUiState.Loaded)?.showingProductModal != null) {
            val productToAdd = (uiState as ProductsUiState.Loaded).showingProductModal!!
            var quantity by remember { mutableIntStateOf(1) }
            BottomModal(
                onDismiss = { viewModel.onEvent(ProductsScreenEvent.DismissQuantityModal) }
            ) {
                AddProductModalContent(
                    productToAdd = productToAdd,
                    quantity = quantity,
                    onAddSelected = { quantity += 1 },
                    onRemoveSelected = { if (quantity > 1) quantity -= 1 },
                    onPurchase = { viewModel.onEvent(ProductsScreenEvent.AddProductToCart(productToAdd, quantity)) }
                )
            }
        }
        // Bottom Sheet section ends -----------------------------------------------------------
    }

}