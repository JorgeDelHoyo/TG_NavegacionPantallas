package com.example.tg_navegacionpantallas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cedulas")
data class Cedula(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fechaCaducidad: String,
    val tecnico: String,
    val viviendaId: Int // FK única para 1:1
)