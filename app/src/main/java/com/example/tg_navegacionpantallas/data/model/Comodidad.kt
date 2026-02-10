package com.example.tg_navegacionpantallas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comodidades")
data class Comodidad(
    @PrimaryKey(autoGenerate = true)
    val comodidadId: Int = 0,
    val nombre: String
)

// TABLA INTERMEDIA (Necesaria para N:M)
@Entity(
    tableName = "vivienda_comodidad_cruzada",
    primaryKeys = ["viviendaId", "comodidadId"]
)
data class ViviendaComodidadCrossRef(
    val viviendaId: Int,
    val comodidadId: Int
)