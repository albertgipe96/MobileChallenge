package com.example.mobilechallenge

import androidx.lifecycle.ViewModel
import com.example.mobilechallenge.common.ui.navigation.AppNavigator
import com.example.mobilechallenge.common.ui.navigation.NavigationIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val appNavigator: AppNavigator
) : ViewModel() {

    init {
        appNavigator.setCustomNavigationChannel(Channel<NavigationIntent>(capacity = Int.MAX_VALUE, onBufferOverflow = BufferOverflow.DROP_LATEST))
    }

}