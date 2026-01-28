package com.example.tg_navegacionpantallas.data.model

import kotlinx.serialization.Serializable

//Clase con @Serializable(id, precio, descripcion)
@Serializable
data class Vivienda(
    val id: Int,
    val modelo: String,
    val descripcion: String,
    val metros: Int,
    val cantidad: Int,
    val precio: Double,
    val imagen: String
)