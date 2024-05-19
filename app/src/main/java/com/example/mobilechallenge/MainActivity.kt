package com.example.mobilechallenge

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobilechallenge.cabifystore.presentation.screens.HomeScreen
import com.example.mobilechallenge.cabifystore.presentation.screens.ProductsScreen
import com.example.mobilechallenge.common.ui.navigation.BottomNavigationBar
import com.example.mobilechallenge.common.ui.navigation.Destination
import com.example.mobilechallenge.common.ui.navigation.NavigationHost
import com.example.mobilechallenge.common.ui.navigation.NavigationIntent
import com.example.mobilechallenge.common.ui.navigation.composable
import com.example.mobilechallenge.ui.theme.M100
import com.example.mobilechallenge.ui.theme.MobileChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.isSplashShow.value
        }
        setContent {
            MainScreen(
                mainViewModel = mainViewModel
            )
        }
    }
}

@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()

    // With this handler, there is no need to pass any callbacks or navController
    NavigationHandler(
        navigationChannel = mainViewModel.appNavigator.navigationChannel,
        navHostController = navController
    )

    MobileChallengeTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(M100),
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { paddingValues ->
            NavigationHost(
                navController = navController,
                startDestination = Destination.HomeScreen,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(destination = Destination.HomeScreen) {
                    Text(text = it.destination.route ?: "")
                    HomeScreen()
                }
                composable(destination = Destination.ProductsScreen) {
                    Text(text = it.destination.route ?: "")
                }
                composable(destination = Destination.PurchasesScreen) {
                    Text(text = it.destination.route ?: "")
                }
            }
        }
    }
}

@Composable
fun NavigationHandler(
    navigationChannel: Channel<NavigationIntent>?,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel?.receiveAsFlow()?.collect { intent ->
            if (activity?.isFinishing == true) return@collect
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    intent.route?.let {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } ?: navHostController.popBackStack()
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}