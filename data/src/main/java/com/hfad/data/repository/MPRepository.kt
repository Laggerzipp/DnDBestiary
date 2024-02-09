package com.hfad.data.repository

import com.domain.DomainPotions
import com.hfad.data.retrofit.MPApiClient
import com.hfad.data.retrofit.toDomain

class MPRepository {
    suspend fun getPotions(): DomainPotions?{
        val response = MPApiClient.apiService.getPotions()
        if(response.isSuccessful){
            return response.body()?.toDomain()
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

}