package com.example.mobilechallenge.cabifystore.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import com.example.mobilechallenge.common.presentation.utils.DateUtils
import com.example.mobilechallenge.ui.theme.M800
import com.example.mobilechallenge.ui.utils.Shape
import com.example.mobilechallenge.ui.utils.Spacing

@Composable
fun PurchaseCard(
    purchase: Purchase
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shape.large.shape)
            .border(1.dp, M800, Shape.large.shape)
            .padding(Spacing.SMALL.spacing),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${purchase.amountSpent}€")
        Text(text = DateUtils.getDateString(purchase.dateInMillis))
    }
}