package com.example.mobilechallenge.cabifystore.data.remote

import com.example.mobilechallenge.cabifystore.data.remote.model.RemoteProductData
import com.example.mobilechallenge.common.data.networkResult.NetworkResult
import retrofit2.http.GET

interface ProductsApi {

    @GET("palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json")
    suspend fun getProducts(): NetworkResult<RemoteProductData>

}