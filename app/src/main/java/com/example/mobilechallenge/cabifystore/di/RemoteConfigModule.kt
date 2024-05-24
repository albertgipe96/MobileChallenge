package com.example.mobilechallenge.cabifystore.di

import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteConfigProvider
import com.example.mobilechallenge.cabifystore.data.remote.dataSource.RemoteConfigProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigModule {

    @Provides
    @Singleton
    fun providesRemoteConfigProvider() : RemoteConfigProvider {
        val remoteConfigProvider = RemoteConfigProviderImpl()
        remoteConfigProvider.init()
        return remoteConfigProvider
    }

}