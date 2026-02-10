package com.example.tg_navegacionpantallas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agentes")
data class Agente(
    @PrimaryKey(autoGenerate = true)
    val agenteId: Int = 0,
    val nombre: String,
    val email: String
)