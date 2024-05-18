package com.example.mobilechallenge.common.di

import com.example.mobilechallenge.cabifystore.data.remote.ProductsApi
import com.example.mobilechallenge.common.data.networkResult.NetworkResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    @ViewModelScoped
    fun providesApiClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.tag("Network").d(message) }).apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    @Provides
    @ViewModelScoped
    fun providesProductsApi(okHttpClient: OkHttpClient): ProductsApi =
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ProductsApi::class.java)

}