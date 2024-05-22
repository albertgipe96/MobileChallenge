package com.example.mobilechallenge.cabifystore.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilechallenge.cabifystore.presentation.uistate.PurchasesUiState
import com.example.mobilechallenge.cabifystore.presentation.vm.PurchasesViewModel
import com.example.mobilechallenge.common.ui.components.TopBar
import com.example.mobilechallenge.common.ui.state.collectWithLifecycle
import com.example.mobilechallenge.ui.theme.M500
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun PurchasesScreen(
    viewModel: PurchasesViewModel = hiltViewModel<PurchasesViewModel>()
) {
    val uiState by viewModel.collectWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(M500),
        topBar = {
            TopBar(
                title = "Purchases"
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when (val state = uiState) {
                PurchasesUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is PurchasesUiState.Error -> {
                    Text(text = state.message)
                }
                is PurchasesUiState.Loaded -> {
                    state.purchases.forEach {
                        Text(text = "${it.amountSpent} - ${LocalDateTime.ofInstant(Instant.ofEpochMilli(it.dateInMillis), ZoneId.systemDefault())}")
                    }
                }
            }
        }
    }
}