package com.example.tg_navegacionpantallas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "etiquetas")
data class Etiqueta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String // Ej: "Oportunidad", "Vistas al mar", "Reformado"
)

// TABLA DE UNIÓN PARA N:M
@Entity(
    tableName = "vivienda_etiqueta_cruce",
    primaryKeys = ["viviendaId", "etiquetaId"]
)
data class ViviendaEtiquetaCrossRef(
    val viviendaId: Int,
    val etiquetaId: Int
)