package com.example.mobilechallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilechallenge.common.ui.navigation.AppNavigator
import com.example.mobilechallenge.common.ui.navigation.NavigationIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val appNavigator: AppNavigator
) : ViewModel() {

    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            appNavigator.setCustomNavigationChannel(Channel<NavigationIntent>(capacity = Int.MAX_VALUE, onBufferOverflow = BufferOverflow.DROP_LATEST))
            delay(300)
            splashShowFlow.emit(false)
        }
    }

}