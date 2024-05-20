package com.example.mobilechallenge.cabifystore.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobilechallenge.R
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.getImageResByProduct
import com.example.mobilechallenge.ui.theme.M100
import com.example.mobilechallenge.ui.theme.M800
import com.example.mobilechallenge.ui.utils.Shape
import com.example.mobilechallenge.ui.utils.Spacing

@Composable
fun ProductCard(
    product: Product,
    onPurchase: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(M100, Shape.large.shape)
            .border(1.dp, M800, Shape.large.shape)
    ) {
        Image(
            modifier = Modifier
                .padding(Spacing.MEDIUM.spacing)
                .weight(1f)
                .clip(Shape.large.shape)
                .border(1.dp, M800, Shape.large.shape),
            painter = painterResource(id = product.getImageResByProduct()),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(Spacing.MEDIUM.spacing)
                .weight(1f)
        ) {
            Text(text = product.name)
            Text(text = "${product.price}€")
            Button(onClick = { onPurchase() }) {
                Text(text = "Add to cart")
            }
        }
    }
}