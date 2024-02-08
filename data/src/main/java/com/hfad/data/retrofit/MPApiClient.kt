package com.hfad.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MPApiClient {
    private const val BASE_URL = "https://api.potterdb.com/v1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: MPApiService by lazy {
        retrofit.create(MPApiService::class.java)
    }
}