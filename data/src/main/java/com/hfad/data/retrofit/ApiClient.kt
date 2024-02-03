package com.hfad.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private var retrofit: Retrofit? = null

    fun getClient():Retrofit{
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl("https://www.dnd5eapi.co/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}