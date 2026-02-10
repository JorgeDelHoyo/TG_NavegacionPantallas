package com.example.tg_navegacionpantallas.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tg_navegacionpantallas.data.local.dao.ViviendaDao
import com.example.tg_navegacionpantallas.data.model.*

@Database(
    entities = [
        Vivienda::class,
        Agente::class,
        Certificado::class,
        Comodidad::class,
        ViviendaComodidadCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ViviendaDatabase : RoomDatabase() {

    abstract fun viviendaDao(): ViviendaDao

    companion object {
        @Volatile
        private var Instance: ViviendaDatabase? = null

        fun getDatabase(context: Context): ViviendaDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ViviendaDatabase::class.java,
                    "vivienda_database" // Nombre del archivo .db
                )
                    // Esto borra la BD si cambias el modelo sin migración (útil para desarrollo)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}