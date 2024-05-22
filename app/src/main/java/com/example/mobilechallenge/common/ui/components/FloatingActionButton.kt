package com.example.mobilechallenge.common.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.mobilechallenge.ui.theme.Y100
import com.example.mobilechallenge.ui.theme.Y500
import com.example.mobilechallenge.ui.utils.Icons

@Composable
fun FloatingActionButton(
    icon: Icons,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Y100,
        contentColor = Y500
    ) {
        Icon(
            painter = painterResource(id = icon.drawable),
            contentDescription = null
        )
    }
}