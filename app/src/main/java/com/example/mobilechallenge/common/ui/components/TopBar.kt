package com.example.mobilechallenge.common.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mobilechallenge.ui.theme.M800
import com.example.mobilechallenge.ui.utils.Icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onNavigateBack: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = M800
            )
        },
        navigationIcon = onNavigateBack?.let { navigateBack ->
            {
                Icon(
                    modifier = Modifier.clickable { navigateBack() },
                    painter = painterResource(id = Icons.BACK_ARROW.drawable),
                    tint = M800,
                    contentDescription = null
                )
            }
        } ?: {},

    )
}