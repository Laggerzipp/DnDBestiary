package com.hfad.data.repository

import android.content.Context
import com.domain.DomainPotion
import com.domain.DomainPotions
import com.hfad.data.database.DbPotion
import com.hfad.data.database.MPDatabase
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

    suspend fun insertPotionDb(context: Context, potion: DomainPotion) {
        val dbPotion = DbPotion(potion)
        val db = MPDatabase.getDb(context)
        db.getDao().insertPotion(dbPotion)
    }
}