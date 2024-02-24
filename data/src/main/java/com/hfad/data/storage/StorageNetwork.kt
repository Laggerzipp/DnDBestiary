package com.hfad.data.storage

import com.domain.models.DomainPotions
import com.hfad.data.models.toDomain
import com.hfad.data.retrofit.RetrofitInstance
import javax.inject.Inject

class StorageNetwork @Inject constructor() : NetworkStorage {
    override suspend fun getPotions(): DomainPotions? {
        val response = RetrofitInstance.api.getPotions()
        if (response.isSuccessful) {
            return response.body()?.toDomain()
        } else {
            throw Exception(response.errorBody().toString())
        }
    }
}