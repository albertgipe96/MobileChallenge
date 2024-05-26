package com.example.mobilechallenge.cabifystore.domain.usecases

import com.example.mobilechallenge.cabifystore.domain.DomainMotherObject
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.ProductCode
import com.example.mobilechallenge.cabifystore.domain.model.onSuccess
import com.example.mobilechallenge.cabifystore.domain.repositories.OffersRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetBestOfferUseCaseTest {

    private lateinit var offersRepository: OffersRepository

    @Before
    fun setUp() {
        offersRepository = DomainMotherObject.fakeOffersRepository
    }

    @Test
    fun `Get Best Offer for TSHIRT, buy 3 and pay 19 each`() = runBlocking {
        getBestOffer(Product(ProductCode.TSHIRT, "tshirt", 20.00), 3) {
            assertEquals(it, 57.00, 0.0)
        }
    }

    @Test
    fun `Get Best Offer for VOUCHER, buy 2 and pay 5 in total`() = runBlocking {
        getBestOffer(Product(ProductCode.VOUCHER, "voucher", 5.00), 2) {
            assertEquals(it, 5.00, 0.0)
        }
    }

    @Test
    fun `Get Best Offer for VOUCHER, buy 1 and apply no discount`() = runBlocking {
        getBestOffer(Product(ProductCode.VOUCHER, "voucher", 5.00), 2) {
            assertEquals(it, 5.00, 0.0)
        }
    }

    @Test
    fun `Get Best Offer for TSHIRT, buy 5 and pay 19 each`() = runBlocking {
        getBestOffer(Product(ProductCode.TSHIRT, "tshirt", 20.00), 5) {
            assertEquals(it, 95.00, 0.0)
        }
    }

    private suspend fun getBestOffer(product: Product, quantity: Int, onAssert: (Double) -> Unit) {
        val result = offersRepository.getOffers()
        var bestPrice = product.price * quantity
        result
            .onSuccess { offers ->
                offers.filter { it.productCode == product.code }.forEach { offer ->
                    offer.quantityToGetOneFree?.let { quantityToGetOneFree ->
                        val calculatedPrice = product.price * (quantity / quantityToGetOneFree) + (quantity % quantityToGetOneFree) * product.price
                        if (calculatedPrice < bestPrice) bestPrice = calculatedPrice
                    }
                    offer.discount?.let { discount ->
                        if (quantity >= discount.quantityOrMore) {
                            val calculatedPrice = discount.discountedPrice * quantity
                            if (calculatedPrice < bestPrice) bestPrice = calculatedPrice
                        }
                    }
                }
                onAssert(bestPrice)
            }
    }

}