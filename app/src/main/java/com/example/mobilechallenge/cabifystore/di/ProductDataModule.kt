package com.example.mobilechallenge.cabifystore.di

import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalProductDataSource
import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalProductDataSourceImpl
import com.example.mobilechallenge.cabifystore.data.local.database.AppDatabase
import com.example.mobilechallenge.cabifystore.data.mappers.ProductDataMapper
import com.example.mobilechallenge.cabifystore.data.remote.ProductsApi
import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteProductDataSource
import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteProductDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Provider

@Module
@InstallIn(ViewModelComponent::class)
object ProductDataModule {

    @Provides
    @ViewModelScoped
    fun providesRemoteProductDataSource(
        productsApi: ProductsApi,
        productDataMapper: ProductDataMapper
    ): RemoteProductDataSource =
        RemoteProductDataSourceImpl(productsApi, productDataMapper)

    @Provides
    @ViewModelScoped
    fun providesLocalProductDataSource(
        appDatabaseProvider: Provider<AppDatabase>,
        productDataMapper: ProductDataMapper
    ): LocalProductDataSource =
        LocalProductDataSourceImpl(appDatabaseProvider, productDataMapper)

    @Provides
    @ViewModelScoped
    fun providesProductDataMapper() = ProductDataMapper()

}