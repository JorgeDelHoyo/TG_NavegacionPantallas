package com.example.tg_navegacionpantallas.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tg_navegacionpantallas.data.model.Propietario
import com.example.tg_navegacionpantallas.data.model.Vivienda

// SOLO 2 ENTIDADES POR AHORA
@Database(
    entities = [Vivienda::class, Propietario::class],
    version = 1 // Si te da error, cambia a 2, 3... o borra la app del emulador
)
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
                )
                    .fallbackToDestructiveMigration() // Esto borra la BD antigua si cambias cosas, ideal para pruebas
                    .build().also { INSTANCE = it }
            }
        }
    }
}