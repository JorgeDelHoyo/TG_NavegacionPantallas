package com.example.tg_navegacionpantallas.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tg_navegacionpantallas.data.model.Vivienda

// Si añades más tablas (Propietario, etc), añádelas aquí en 'entities'
@Database(entities = [Vivienda::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun viviendaDao(): ViviendaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "viviendas_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}