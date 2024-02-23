package com.dndbestiary.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dndbestiary.MainAdapter
import com.domain.models.DomainPotion
import com.domain.usecase.DeletePotionFromDbUseCase
import com.domain.usecase.GetPotionsFromDbUseCase
import com.domain.usecase.GetPotionsUseCase
import com.domain.usecase.InsertPotionIntoDbUseCase
import com.hfad.data.repository.MPRepositoryImpl
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(repository: MPRepositoryImpl) : ViewModel() {
    private val insertPotionIntoDbUseCase = InsertPotionIntoDbUseCase(repository)
    private val getPotionsUseCase = GetPotionsUseCase(repository)
    private val deletePotionFromDbUseCase = DeletePotionFromDbUseCase(repository)
    private val getPotionsFromDbUseCase = GetPotionsFromDbUseCase(repository)
    private var potionList: List<DomainPotion> = emptyList()

    fun setPotionList(potions: List<DomainPotion>) {
        potionList = potions
    }
    fun getPotions(): LiveData<List<DomainPotion>> {
        return liveData(Dispatchers.IO) {
            try {
                val request = getPotionsUseCase.execute()
                if (request != null) {
                    Log.d("ApiRequest", "Api request successful")

                    val updatedPotions = request.potions.toMutableList()
                    val favoritePotions = getPotionsFromDbUseCase.execute()
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
                emit(emptyList())
            }
        }
    }

    fun getPotionsFromDb(): LiveData<List<DomainPotion>> {
        return liveData(Dispatchers.IO) {
            try {
                val potionList = getPotionsFromDbUseCase.execute()
                Log.d("DBOperation", "Db request successful")
                potionList?.let { emit(it) }
            } catch (e: Exception) {
                Log.d("DBOperation", "Db request failed")
                emit(emptyList())
            }
        }
    }

    fun insertPotionIntoDb(potion: DomainPotion){
        loadImageForPotion(potion)
        viewModelScope.launch(Dispatchers.IO) {
            insertPotionIntoDbUseCase.execute(potion = potion)
            Log.d("DBOperation", "Potion inserted into database: $potion")
        }
    }

    fun deletePotionFromDbByIndex(potionId: String){
        viewModelScope.launch(Dispatchers.IO) {
            deletePotionFromDbUseCase.execute(potionId = potionId)
            Log.d("DBOperation", "Potion id deleted from database: $potionId")
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

    private fun loadImageForPotion(potion: DomainPotion) {
        Picasso.get().load(potion.image).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap != null) {
                    Log.d("Picasso", "Image loaded successfully")
                    potion.bitmapImage = bitmap
                } else {
                    Log.e("Picasso", "Error: Received empty bitmap")
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.e("Picasso", "Error loading image: $e")
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                Log.d("Picasso", "Image loading started...")
            }
        })
    }
}