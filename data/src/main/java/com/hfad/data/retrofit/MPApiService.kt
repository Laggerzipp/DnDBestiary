package com.hfad.data.retrofit

import com.hfad.data.models.PotionResponse
import retrofit2.Response
import retrofit2.http.GET

interface MPApiService {
    @GET("potions")
    suspend fun getPotions(): Response<PotionResponse>
}