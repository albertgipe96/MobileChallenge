package com.example.mobilechallenge.common.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mobilechallenge.R

sealed class BottomNavItem(val route: String, val icon: ImageVector, @StringRes val label: Int) {
    object Home : BottomNavItem("home", Icons.Default.Home, R.string.tab_bar_home)
    object Products : BottomNavItem("products", Icons.Default.Menu, R.string.tab_bar_products)
    object Purchases : BottomNavItem("purchases", Icons.Default.Person, R.string.tab_bar_purchases)
}