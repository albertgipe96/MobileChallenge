package com.example.mobilechallenge.cabifystore.di

import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteProductDataSource
import com.example.mobilechallenge.cabifystore.data.repositories.ProductsRepositoryImpl
import com.example.mobilechallenge.cabifystore.domain.repositories.ProductsRepository
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
    fun providesProductsRepository(remoteProductDataSource: RemoteProductDataSource): ProductsRepository =
        ProductsRepositoryImpl(remoteProductDataSource)

}