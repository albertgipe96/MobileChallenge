package com.example.mobilechallenge.cabifystore.domain

import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalProductDataSource
import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalProductDataSourceImpl
import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalPurchaseDataSource
import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalPurchaseDataSourceImpl
import com.example.mobilechallenge.cabifystore.data.local.database.AppDatabase
import com.example.mobilechallenge.cabifystore.data.mappers.ProductDataMapper
import com.example.mobilechallenge.cabifystore.data.mappers.PurchaseDataMapper
import com.example.mobilechallenge.cabifystore.data.remote.ProductsApi
import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteProductDataSourceImpl
import com.example.mobilechallenge.cabifystore.data.remote.model.RemoteProduct
import com.example.mobilechallenge.cabifystore.data.remote.model.RemoteProductData
import com.example.mobilechallenge.cabifystore.data.repositories.ProductsRepositoryImpl
import com.example.mobilechallenge.cabifystore.data.repositories.PurchasesRepositoryImpl
import com.example.mobilechallenge.cabifystore.domain.model.CartProduct
import com.example.mobilechallenge.cabifystore.domain.model.Offer
import com.example.mobilechallenge.cabifystore.domain.model.Product
import com.example.mobilechallenge.cabifystore.domain.model.ProductCode
import com.example.mobilechallenge.cabifystore.domain.model.Purchase
import com.example.mobilechallenge.cabifystore.domain.model.QuantityDiscount
import com.example.mobilechallenge.cabifystore.domain.model.Resource
import com.example.mobilechallenge.cabifystore.domain.repositories.OffersRepository
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import com.example.mobilechallenge.cabifystore.domain.repositories.PurchasesRepository
import com.example.mobilechallenge.cabifystore.domain.usecases.GetBestOfferUseCase
import com.example.mobilechallenge.common.data.networkResult.NetworkResult
import javax.inject.Provider

object DomainMotherObject {

    val fakeMapper = ProductDataMapper()
    val fakeApiResponse = NetworkResult.Success(RemoteProductData(
        listOf(
            RemoteProduct("TSHIRT", "tshirt", 10.0),
            RemoteProduct("VOUCHER", "voucher", 5.0),
            RemoteProduct("MUG", "mug", 2.0)
        )
    ))
    val fakeApi = object : ProductsApi {
        override suspend fun getProducts(): NetworkResult<RemoteProductData> {
            return fakeApiResponse
        }
    }
    val fakeRemoteDataSource = RemoteProductDataSourceImpl(fakeApi, fakeMapper)
    val fakeLocalResponse = listOf(
        CartProduct(0, ProductCode.TSHIRT, "tshirt", 10.0),
        CartProduct(1, ProductCode.VOUCHER, "voucher", 5.0),
        CartProduct(2, ProductCode.MUG, "mug", 2.0)
    )
    val fakeProducts = listOf(
        Product(ProductCode.TSHIRT, "tshirt", 10.0),
        Product(ProductCode.VOUCHER, "voucher", 5.0),
        Product(ProductCode.MUG, "mug", 2.0)
    )
    val fakeLocalDataSource = object : LocalProductDataSource {
        override suspend fun getCartProducts(): Resource<List<CartProduct>> {
            return Resource.Success(fakeLocalResponse)
        }

        override suspend fun addProductToCart(product: List<Product>): Resource<Unit> {
            return Resource.Success(Unit)
        }

        override suspend fun removeProductFromCart(product: CartProduct): Resource<Unit> {
            return Resource.Success(Unit)
        }

        override suspend fun emptyCart(): Resource<Unit> {
            return Resource.Success(Unit)
        }
    }
    val fakePurchases = listOf(
        Purchase(100.0, 1234),
        Purchase(90.0, 1111),
        Purchase(50.0, 2222),
    )
    val fakePurchaseDataSource = object : LocalPurchaseDataSource {
        override suspend fun getUserPurchases(): Resource<List<Purchase>> {
            return Resource.Success(fakePurchases)
        }

        override suspend fun saveUserPurchase(purchase: Purchase): Resource<Unit> {
            return Resource.Success(Unit)
        }
    }

    val fakeProductsRepository = ProductsRepositoryImpl(fakeRemoteDataSource, fakeLocalDataSource)
    val fakePurchasesRepository = PurchasesRepositoryImpl(fakePurchaseDataSource)

    val fakeOffers = listOf(
        Offer(ProductCode.TSHIRT, null, QuantityDiscount(3, 19.00)),
        Offer(ProductCode.VOUCHER, 2, null)
    )
    val fakeOffersRepository = object : OffersRepository {
        override suspend fun getOffers(): Resource<List<Offer>> {
            return Resource.Success(fakeOffers)
        }

    }
}