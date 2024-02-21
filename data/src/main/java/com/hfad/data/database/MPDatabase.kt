package com.hfad.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.domain.DomainPotion

@Database(entities = [DomainPotion::class], version = 1)
@TypeConverters(Converters::class)
abstract class MPDatabase: RoomDatabase() {
    abstract fun getDao(): MPDao

    companion object{
        private var INSTANCE: MPDatabase? = null
        fun getDb(context: Context): MPDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MPDatabase::class.java,
                    "MPCatalogDb"
                ).fallbackToDestructiveMigration().build()
                Log.d("DbInstance", "Database created successfully")
            } else {
                Log.d("DbInstance", "Using existing database instance")
            }
            return INSTANCE!!
        }
    }
}