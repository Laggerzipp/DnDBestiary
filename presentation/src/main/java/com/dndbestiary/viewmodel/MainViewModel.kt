package com.dndbestiary.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dndbestiary.MainAdapter
import com.domain.models.DomainPotion
import com.domain.usecase.DeletePotionFromDbUseCase
import com.domain.usecase.GetPotionsFromDbUseCase
import com.domain.usecase.GetPotionsUseCase
import com.domain.usecase.InsertPotionIntoDbUseCase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val insertPotionIntoDbUseCase: InsertPotionIntoDbUseCase,
    private val getPotionsUseCase: GetPotionsUseCase,
    private val deletePotionFromDbUseCase: DeletePotionFromDbUseCase,
    private val getPotionsFromDbUseCase: GetPotionsFromDbUseCase
) : ViewModel() {

    private var _potionList = MutableLiveData<List<DomainPotion>>()
    var potionList: LiveData<List<DomainPotion>> = _potionList
    private var _potionListDb = MutableLiveData<List<DomainPotion>>()
    var potionListDb: LiveData<List<DomainPotion>> = _potionListDb
    var potion: DomainPotion? = null

    fun getPotions() {
        try {
            var updatedPotions: MutableList<DomainPotion>
            viewModelScope.launch(Dispatchers.IO) {
                val request = getPotionsUseCase.execute()
                if (request != null) {
                    Log.d("ApiRequest", "Api request successful")

                    updatedPotions = request.potions.toMutableList()
                    val favoritePotions = getPotionsFromDbUseCase.execute()
                    favoritePotions?.let { favorites ->
                        for (i in updatedPotions.indices) {
                            val foundFavorite = favorites.find { it.potionId == updatedPotions[i].potionId }
                            foundFavorite?.let { favorite ->
                                updatedPotions[i] = favorite
                            }
                        }
                    }
                    _potionList.postValue(updatedPotions.toList())
                } else {
                    Log.d("ApiRequest", "Api request failed")
                }
            }
        }
        catch (e: Exception) {
            Log.e("ApiRequest", "Exception occurred", e)
        }
    }

    fun getPotionsFromDb(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _potionListDb.postValue(getPotionsFromDbUseCase.execute())
                Log.d("DBOperation", "Db request successful")
            } catch (e: Exception) {
                Log.d("DBOperation", "Db request failed")
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
            getPotionsFromDb()
        }
    }

    fun getPotionById(potionId: String, potionImage: String): DomainPotion?{
        return _potionList.value?.find { it.potionId == potionId }?.apply {
            if (image == null) {
                image = potionImage
            }
        }
    }

    fun getPotionByIdOffline(potionId: String, potionImage: String): DomainPotion?{
        return _potionListDb.value?.find { it.potionId == potionId }?.apply {
            if (image == null) {
                image = potionImage
            }
        }
    }

    fun searchPotionByName(adapter: MainAdapter, text: String?) {
        val filteredPotions = if (text.isNullOrBlank()) {
            potionList
        } else {
            val searchText = text.lowercase().trim()
            MutableLiveData<List<DomainPotion>>(potionList.value?.filter { it.name.lowercase().contains(searchText) })
        }
        adapter.submitList(filteredPotions.value)
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