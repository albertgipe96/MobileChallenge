package com.example.mobilechallenge.common.ui.navigation

import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

interface AppNavigator {
    var navigationChannel: Channel<NavigationIntent>?
    fun setCustomNavigationChannel(channel: Channel<NavigationIntent>)
    suspend fun navigateTo(route: String, popUpToRoute: String? = null, inclusive: Boolean = false, isSingleTop: Boolean = false)
    suspend fun navigateBack(route: String? = null, inclusive: Boolean = false)
}

sealed class NavigationIntent {
    data class NavigateTo(val route: String, val popUpToRoute: String? = null, val inclusive: Boolean = false, val isSingleTop: Boolean = false) : NavigationIntent()
    data class NavigateBack(val route: String? = null, val inclusive: Boolean = false) : NavigationIntent()
}

class AppNavigatorImpl @Inject constructor() : AppNavigator {

    override var navigationChannel: Channel<NavigationIntent>? = null
    override fun setCustomNavigationChannel(channel: Channel<NavigationIntent>) {
        navigationChannel = channel
    }

    override suspend fun navigateTo(route: String, popUpToRoute: String?, inclusive: Boolean, isSingleTop: Boolean) {
        navigationChannel?.trySend(NavigationIntent.NavigateTo(route, popUpToRoute, inclusive, isSingleTop))
    }

    override suspend fun navigateBack(route: String?, inclusive: Boolean) {
        navigationChannel?.trySend(NavigationIntent.NavigateBack(route, inclusive))
    }
}