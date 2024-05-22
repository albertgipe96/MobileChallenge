package com.example.mobilechallenge.cabifystore.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobilechallenge.cabifystore.domain.model.CartProduct
import com.example.mobilechallenge.cabifystore.domain.model.ProductCode
import com.example.mobilechallenge.cabifystore.domain.model.getImageResByProductCode
import com.example.mobilechallenge.common.ui.components.CabifyButton
import com.example.mobilechallenge.ui.utils.Spacing

@Composable
fun CartProductsModalContent(
    cartProductsMap: Map<ProductCode, List<CartProduct>>,
    onPurchaseCart: () -> Unit
) {
    Column(modifier = Modifier.padding(Spacing.MEDIUM.spacing)) {
        cartProductsMap.forEach { map ->
            val cartProductList = map.value
            val quantity = cartProductList.size
            val cartProduct = cartProductList.first()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.MEDIUM.spacing),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(id = cartProduct.code.getImageResByProductCode()),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Spacing.MEDIUM.spacing))
                    Column {
                        Text(
                            text = cartProduct.name
                        )
                        Text(
                            text = "${cartProduct.realPrice}€"
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "x$quantity",
                        fontSize = 20.sp
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            CabifyButton(
                text = "Add to cart",
                onClick = onPurchaseCart
            )
        }
        Spacer(modifier = Modifier.height(Spacing.EXTRA_LARGE.spacing))
    }
}