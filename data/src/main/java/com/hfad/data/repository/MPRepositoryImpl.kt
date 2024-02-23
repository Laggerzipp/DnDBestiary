package com.hfad.data.repository

import com.domain.repository.MPRepository
import com.domain.models.DomainPotion
import com.domain.models.DomainPotions
import com.hfad.data.database.MPDatabase
import com.hfad.data.retrofit.RetrofitInstance
import com.hfad.data.models.toDomain

class MPRepositoryImpl(private val db: MPDatabase): MPRepository {
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