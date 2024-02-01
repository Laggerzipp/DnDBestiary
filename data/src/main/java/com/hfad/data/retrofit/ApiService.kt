package com.hfad.data.retrofit

import com.example.example.Monster
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("monsters")
    suspend fun getMonsterList(): MonsterGeneralList
    @GET("monsters/{id}")
    suspend fun getMonsterByIndex(@Path("id") id:String): Monster
}