package com.example.mobilechallenge.cabifystore.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilechallenge.cabifystore.domain.model.Offer
import com.example.mobilechallenge.cabifystore.domain.model.getImageResByProductCode
import com.example.mobilechallenge.ui.theme.M500
import com.example.mobilechallenge.ui.theme.M800
import com.example.mobilechallenge.ui.theme.M900
import com.example.mobilechallenge.ui.utils.Shape
import com.example.mobilechallenge.ui.utils.Spacing

@Composable
fun OfferCard(
    offer: Offer,
    onOfferClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(Shape.large.shape)
            .border(1.dp, M800, Shape.large.shape)
            .clickable { onOfferClick() }
            .paint(
                painter = painterResource(id = offer.productCode.getImageResByProductCode()),
                contentScale = ContentScale.Inside, alpha = 0.3f
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(M500.copy(alpha = 0.3f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "New offer")
            offer.quantityToGetOneFree?.let { quantityToGetOneFree ->
                Text(
                    text = "${quantityToGetOneFree} X 1",
                    color = M900,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            offer.discount?.let { discount ->
                Text(
                    text = "Get ${discount.quantityOrMore} or more",
                    color = M900,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "${discount.discountedPrice}€/u",
                    color = M900,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }

}