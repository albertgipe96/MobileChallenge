package com.example.mobilechallenge.cabifystore.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.getImageResByProductCode
import com.example.mobilechallenge.common.ui.components.CabifyButton
import com.example.mobilechallenge.ui.theme.M800
import com.example.mobilechallenge.ui.theme.neutral_300
import com.example.mobilechallenge.ui.utils.Icons
import com.example.mobilechallenge.ui.utils.Spacing

@Composable
fun AddProductModalContent(
    productToAdd: Product,
    quantity: Int,
    onAddSelected: () -> Unit,
    onRemoveSelected: () -> Unit,
    onPurchase: () -> Unit
) {
    Column(modifier = Modifier.padding(Spacing.MEDIUM.spacing)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = productToAdd.code.getImageResByProductCode()),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(Spacing.MEDIUM.spacing))
                Column {
                    Text(
                        text = productToAdd.name
                    )
                    Text(
                        text = "${productToAdd.price}€"
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onRemoveSelected() },
                    painter = painterResource(id = Icons.LESS.drawable),
                    tint = if (quantity > 1) M800 else neutral_300,
                    contentDescription = null
                )
                Text(
                    text = quantity.toString(),
                    fontSize = 20.sp
                )
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onAddSelected() },
                    painter = painterResource(id = Icons.ADD.drawable),
                    tint = M800,
                    contentDescription = null
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            CabifyButton(
                text = "Add to cart",
                onClick = onPurchase
            )
        }
        Spacer(modifier = Modifier.height(Spacing.EXTRA_LARGE.spacing))
    }
}