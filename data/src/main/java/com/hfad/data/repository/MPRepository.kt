package com.hfad.data.repository

import com.domain.IMPRepository
import com.domain.model.DomainPotion
import com.domain.model.DomainPotions
import com.hfad.data.database.MPDatabase
import com.hfad.data.retrofit.RetrofitInstance
import com.hfad.data.retrofit.toDomain

class MPRepository(private val db: MPDatabase): IMPRepository {
    override suspend fun getPotions(): DomainPotions?{
        val response = RetrofitInstance.api.getPotions()
        if(response.isSuccessful){
            return response.body()?.toDomain()
        } else {
            throw Exception(response.errorBody().toString())
        }
    }

    override suspend fun insertPotionDb(potion: DomainPotion){
      return db.getDao().insertPotion(potion)
    }
    override suspend fun getPotionsFromDb(): List<DomainPotion>? {
       return db.getDao().getPotionsFromDb()
   }

    override suspend fun deletePotionFromDbByIndex(potionIndex: String){
        db.getDao().deletePotionByIndex(potionIndex)
   }
}