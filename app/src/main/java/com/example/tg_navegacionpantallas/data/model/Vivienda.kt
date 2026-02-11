package com.example.tg_navegacionpantallas.data.model

import androidx.room.Entity      // <--- IMPORTANTE
import androidx.room.PrimaryKey  // <--- IMPORTANTE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "viviendas") // <--- ESTO ES OBLIGATORIO PARA ROOM
data class Vivienda(
    @PrimaryKey(autoGenerate = true) // <--- ESTO ES OBLIGATORIO
    val id: Int = 0, // Pon un valor por defecto (= 0) para que el auto-generate funcione bien
    val modelo: String,
    val descripcion: String,
    val metros: Int,
    val cantidad: Int,
    val precio: Double,
    val imagen: String,

    val propietarioId: Int? = null,
)