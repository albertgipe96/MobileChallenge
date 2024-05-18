package com.example.mobilechallenge.cabifystore.di

import com.example.mobilechallenge.cabifystore.data.remote.ProductsApi
import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteProductDataSource
import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteProductDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RemoteProductModule {

    @Provides
    @ViewModelScoped
    fun providesRemoteProductDataSource(productsApi: ProductsApi): RemoteProductDataSource =
        RemoteProductDataSourceImpl(productsApi)

}