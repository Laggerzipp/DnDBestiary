package com.hfad.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("potions")
    suspend fun getPotions(): PotionResponse
    @GET("potions/{id}")
    suspend fun getPotionById(@Path("id") id: String?): Potion
}