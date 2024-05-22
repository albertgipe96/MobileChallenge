package com.example.mobilechallenge.common.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilechallenge.ui.theme.M100
import com.example.mobilechallenge.ui.theme.M800
import com.example.mobilechallenge.ui.theme.M900
import com.example.mobilechallenge.ui.utils.Shape
import com.example.mobilechallenge.ui.utils.Spacing

@Composable
fun CabifyButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(Spacing.MEDIUM.spacing),
        shape = Shape.medium.shape,
        border = BorderStroke(1.dp, M900),
        colors = ButtonDefaults.buttonColors(
            containerColor = M800,
            contentColor = M100
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            color = M100
        )
    }
}