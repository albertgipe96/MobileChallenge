package com.example.mobilechallenge.cabifystore.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilechallenge.cabifystore.presentation.uistate.HomeUiState
import com.example.mobilechallenge.cabifystore.presentation.uistate.PurchasesUiState
import com.example.mobilechallenge.cabifystore.presentation.vm.HomeViewModel
import com.example.mobilechallenge.common.ui.components.TopBar
import com.example.mobilechallenge.common.ui.state.collectWithLifecycle
import com.example.mobilechallenge.ui.theme.M500
import com.example.mobilechallenge.ui.utils.Spacing
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),

) {
    val uiState by viewModel.collectWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(M500),
        topBar = {
            TopBar(
                title = "Welcome to Cabify Store,\nCheck out new offers"
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            when (val state = uiState) {
                HomeUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is HomeUiState.Error -> {
                    Text(text = state.message)
                }
                is HomeUiState.Loaded -> {
                    state.offers.forEach {
                        Text(text = "code: ${it.productCode}\n" +
                                "quantityToGetOneFree: ${it.quantityToGetOneFree}\n" +
                                "discount: ${it.discount?.quantityOrMore}, newPrice: ${it.discount?.discountedPrice}")
                        Spacer(modifier = Modifier.height(Spacing.MEDIUM.spacing))
                    }
                }
            }
        }
    }
}