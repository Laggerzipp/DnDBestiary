package com.dndbestiary.libraryfragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.domain.DomainPotion
import com.hfad.data.database.MPDatabase
import com.hfad.data.repository.MPRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibraryViewModel(context: Context) : ViewModel() {
    private val repository = MPRepository(MPDatabase.getDb(context))
    private var potionList: List<DomainPotion> = emptyList()

    fun setPotionList(potions: List<DomainPotion>) {
        potionList = potions
    }
    fun insertPotionIntoDb(potion: DomainPotion) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertPotionDb(potion)
        }
    }

    fun deletePotionFromDbByIndex(potionId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deletePotionFromDbByIndex(potionId)
        }
    }

    fun getPotionById(potionId: String, potionImage: String): DomainPotion? {
        return potionList.find { it.potionId == potionId }?.apply {
            if (image == null) {
                image = potionImage
            }
        }
    }

    fun getPotionsFromDb(): LiveData<List<DomainPotion>> {
        return liveData(Dispatchers.IO) {
            try {
                val potionList = repository.getPotionsFromDb()
                Log.d("DbRequest", "Db request successful")
                potionList?.let { emit(it) }
            } catch (e: Exception) {
                Log.d("DbRequest", "Db request failed")
                emit(emptyList())
            }
        }
    }
}