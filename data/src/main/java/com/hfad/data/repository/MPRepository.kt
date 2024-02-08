package com.hfad.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.domain.DomainPotion
import com.hfad.data.retrofit.MPApiClient
import com.hfad.data.retrofit.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MPRepository {
    private val _potionList = MutableLiveData<List<DomainPotion>>()
    val potionList: LiveData<List<DomainPotion>> = _potionList

    init {
        fetchPotions()
    }

    private fun fetchPotions() {
        CoroutineScope(Dispatchers.IO).launch {
            val potionResponse = MPApiClient.apiService.getPotions()
            val domainPotions = potionResponse.potions.map { it.toDomain() }
            _potionList.postValue(domainPotions)
        }
    }

}