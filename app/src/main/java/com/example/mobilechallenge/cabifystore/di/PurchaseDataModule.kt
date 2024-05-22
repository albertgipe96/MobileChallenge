package com.example.mobilechallenge.cabifystore.di

import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalPurchaseDataSource
import com.example.mobilechallenge.cabifystore.data.local.dataSource.LocalPurchaseDataSourceImpl
import com.example.mobilechallenge.cabifystore.data.local.database.AppDatabase
import com.example.mobilechallenge.cabifystore.data.mappers.PurchaseDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Provider

@Module
@InstallIn(ViewModelComponent::class)
object PurchaseDataModule {

    @Provides
    @ViewModelScoped
    fun providesPurchaseLocalDataSource(
        appDatabaseProvider: Provider<AppDatabase>,
        purchaseDataMapper: PurchaseDataMapper
    ) : LocalPurchaseDataSource =
        LocalPurchaseDataSourceImpl(appDatabaseProvider, purchaseDataMapper)

}