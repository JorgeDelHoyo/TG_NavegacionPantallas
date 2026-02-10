package com.example.tg_navegacionpantallas.data.local.dao

import androidx.room.*
import com.example.tg_navegacionpantallas.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ViviendaDao {

    // --- CRUD VIVIENDA ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVivienda(vivienda: Vivienda)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(viviendas: List<Vivienda>)

    @Update
    suspend fun actualizarVivienda(vivienda: Vivienda)

    @Delete
    suspend fun eliminarVivienda(vivienda: Vivienda)

    @Query("SELECT * FROM viviendas")
    fun getAllViviendas(): Flow<List<Vivienda>>

    @Query("SELECT COUNT(*) FROM viviendas")
    suspend fun contarViviendas(): Int

    // --- CRUD AGENTE (Necesario crear uno al principio) ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAgente(agente: Agente)

    // --- CONSULTAS CON RELACIONES (Requisito Académico) ---

    // Obtener viviendas con sus extras (N:M)
    @Transaction
    @Query("SELECT * FROM viviendas")
    fun getViviendasConComodidades(): Flow<List<ViviendaConComodidades>>

    // Obtener vivienda con su certificado (1:1)
    @Transaction
    @Query("SELECT * FROM viviendas WHERE id = :id")
    suspend fun getViviendaConCertificado(id: Int): ViviendaConCertificado
}