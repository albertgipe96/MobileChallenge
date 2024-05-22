package com.example.mobilechallenge.cabifystore.di

import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalProductDataSource
import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalPurchaseDataSource
import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteProductDataSource
import com.example.mobilechallenge.cabifystore.data.repositories.ProductsRepositoryImpl
import com.example.mobilechallenge.cabifystore.data.repositories.PurchasesRepositoryImpl
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
import com.example.mobilechallenge.cabifystore.domain.repositories.PurchasesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoriesModule {

    @Provides
    @ViewModelScoped
    fun providesProductsRepository(
        remoteProductDataSource: RemoteProductDataSource,
        localProductDataSource: LocalProductDataSource
    ): ProductsRepository =
        ProductsRepositoryImpl(remoteProductDataSource, localProductDataSource)

    @Provides
    @ViewModelScoped
    fun providesPurchasesRepository(
        localPurchaseDataSource: LocalPurchaseDataSource
    ): PurchasesRepository =
        PurchasesRepositoryImpl(localPurchaseDataSource)

}