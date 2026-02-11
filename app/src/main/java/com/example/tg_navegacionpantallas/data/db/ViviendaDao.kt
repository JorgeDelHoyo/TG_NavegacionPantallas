package com.example.tg_navegacionpantallas.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tg_navegacionpantallas.data.model.Vivienda
import kotlinx.coroutines.flow.Flow

@Dao
interface ViviendaDao {
    @Query("SELECT * FROM viviendas")
    fun obtenerTodas(): Flow<List<Vivienda>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(vivienda: Vivienda)
}