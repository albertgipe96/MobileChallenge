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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilechallenge.cabifystore.presentation.components.PurchaseCard
import com.example.mobilechallenge.cabifystore.presentation.uistate.PurchasesUiState
import com.example.mobilechallenge.cabifystore.presentation.vm.PurchasesViewModel
import com.example.mobilechallenge.common.ui.components.TopBar
import com.example.mobilechallenge.common.ui.state.collectWithLifecycle
import com.example.mobilechallenge.ui.theme.M500
import com.example.mobilechallenge.ui.utils.Spacing
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.MEDIUM.spacing)
                .verticalScroll(rememberScrollState())
        ) {
            when (val state = uiState) {
                PurchasesUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is PurchasesUiState.Error -> {
                    Text(text = state.message)
                }
                is PurchasesUiState.Loaded -> {
                    state.purchases.forEach {
                        PurchaseCard(purchase = it)
                        Spacer(modifier = Modifier.height(Spacing.MEDIUM.spacing))
                    }
                    Spacer(modifier = Modifier.height(Spacing.EXTRA_LARGE.spacing))
                }
            }
        }
    }
}