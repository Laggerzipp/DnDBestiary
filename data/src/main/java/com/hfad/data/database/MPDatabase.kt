package com.hfad.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbPotion::class], version = 1)
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
                Log.d("Db", "Database created successfully")
            } else {
                Log.d("Db", "Using existing database instance")
            }
            return INSTANCE!!
        }
    }
}