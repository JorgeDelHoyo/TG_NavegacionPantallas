package com.example.tg_navegacionpantallas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

//Clase con @Serializable(id, precio, descripcion)
@Serializable
@Entity(tableName = "viviendas")
data class Vivienda(
    @PrimaryKey
    val id: Int,
    val modelo: String,
    val descripcion: String,
    val metros: Int,
    val cantidad: Int,
    val precio: Double,
    val imagen: String
)