package com.dndbestiary.di

import android.content.Context
import com.domain.repository.MPRepository
import com.hfad.data.repository.MPRepositoryImpl
import com.hfad.data.storage.NetworkStorage
import com.hfad.data.storage.PotionStorage
import com.hfad.data.storage.StorageNetwork
import com.hfad.data.storage.StoragePotion
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providePotionStorage(context: Context): PotionStorage {
        return StoragePotion(context = context)
    }

    @Provides
    fun provideNetworkStorage(): NetworkStorage {
        return StorageNetwork()
    }

    @Provides
    fun provideMPRepository(
        potionStorage: PotionStorage,
        networkStorage: StorageNetwork,
    ): MPRepository {
        return MPRepositoryImpl(potionStorage = potionStorage, networkStorage = networkStorage)
    }
}