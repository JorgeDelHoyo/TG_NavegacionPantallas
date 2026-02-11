package com.example.tg_navegacionpantallas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "propietarios")
data class Propietario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val telefono: String
)