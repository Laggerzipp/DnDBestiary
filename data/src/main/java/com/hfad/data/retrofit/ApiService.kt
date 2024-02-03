package com.hfad.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("potions")
    suspend fun getPotions(): PotionResponse
    @GET("potions/{id}")
    suspend fun getPotionByIndex(@Path("id") id:String): Potion
}