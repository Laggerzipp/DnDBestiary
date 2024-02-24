package com.hfad.data.storage

import com.domain.models.DomainPotions

interface NetworkStorage {
    suspend fun getPotions(): DomainPotions?
}