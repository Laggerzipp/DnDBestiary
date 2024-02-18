package com.dndbestiary.mainfragment

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

class MainViewModel(context: Context) : ViewModel() {
    private val repository = MPRepository(MPDatabase.getDb(context))
    private var potionList: List<DomainPotion> = emptyList()

    fun setPotionList(potions: List<DomainPotion>) {
        potionList = potions
    }
    fun getPotions(): LiveData<List<DomainPotion>> {
        return liveData(Dispatchers.IO) {
            try {
                val request = repository.getPotions()
                if (request != null) {
                    Log.d("ApiRequest", "Api request successful")

                    val updatedPotions = request.potions.toMutableList()
                    val favoritePotions = repository.getPotionsFromDb()
                    favoritePotions?.let { favorites ->
                        for (i in updatedPotions.indices) {
                            val foundFavorite = favorites.find { it.potionId == updatedPotions[i].potionId }
                            foundFavorite?.let { favorite ->
                                updatedPotions[i] = favorite
                            }
                        }
                    }

                    emit(updatedPotions.toList())
                } else {
                    Log.d("ApiRequest", "Api request failed")
                }
            } catch (e: Exception) {
                Log.e("ApiRequest", "Exception occurred", e)
                emit(emptyList<DomainPotion>())
            }
        }
    }

    fun insertPotionIntoDb(potion: DomainPotion){
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertPotionDb(potion)
        }
    }
    fun deletePotionFromDbByIndex(potionId: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deletePotionFromDbByIndex(potionId)
        }
    }

    fun getPotionById(potionId: String, potionImage: String): DomainPotion?{
        return potionList.find { it.potionId == potionId }?.apply {
            if (image == null) {
                image = potionImage
            }
        }
    }

    fun searchPotionByName(adapter: MainAdapter, text: String?): Boolean {
        val filteredPotions = if (text.isNullOrBlank()) {
            potionList
        } else {
            val searchText = text.lowercase().trim()
            potionList.filter { it.name.lowercase().contains(searchText) }
        }
        adapter.submitList(filteredPotions)
        return true
    }
}