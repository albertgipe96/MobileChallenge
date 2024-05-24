package com.example.mobilechallenge.cabifystore.presentation.uistate

import com.example.mobilechallenge.cabifystore.domain.model.Offer
import javax.annotation.concurrent.Immutable

@Immutable
sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Error(val message: String) : HomeUiState()
    data class Loaded(val offers: List<Offer>) : HomeUiState()
}

sealed interface HomeEvent {

}