package com.example.tg_navegacionpantallas.data.model

//Clase con @Serializable(id, precio, descripcion)
data class Vivienda(
    val id: Int,
    val modelo: String,
    val descripcion: String,
    val metros: Int,
    val cantidad: Int,
    val precio: Double,
    val imagenResId: String
)