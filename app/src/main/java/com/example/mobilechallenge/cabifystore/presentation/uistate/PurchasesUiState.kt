package com.example.mobilechallenge.cabifystore.presentation.uistate

import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import javax.annotation.concurrent.Immutable

@Immutable
sealed class PurchasesUiState {
    data object Loading : PurchasesUiState()
    data class Error(val message: String) : PurchasesUiState()
    data class Loaded(val purchases: List<Purchase>) : PurchasesUiState()
}

sealed interface PurchasesEvent {

}