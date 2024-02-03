package com.hfad.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private var retrofit: Retrofit? = null

    fun getClient():Retrofit{
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.potterdb.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}