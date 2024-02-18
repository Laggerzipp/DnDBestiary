package com.hfad.data.repository

import com.domain.DomainPotion
import com.domain.DomainPotions
import com.hfad.data.database.MPDatabase
import com.hfad.data.retrofit.RetrofitInstance
import com.hfad.data.retrofit.toDomain

class MPRepository(private val db: MPDatabase) {
    suspend fun getPotions(): DomainPotions?{
        val response = RetrofitInstance.api.getPotions()
        if(response.isSuccessful){
            return response.body()?.toDomain()
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

   suspend fun insertPotionDb(potion: DomainPotion) = db.getDao().insertPotion(potion)
   suspend fun getPotionsFromDb(): List<DomainPotion>? {
       return db.getDao().getPotionsFromDb()
   }

}