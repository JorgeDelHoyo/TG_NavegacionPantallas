package com.example.tg_navegacionpantallas.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "certificados",
    foreignKeys = [
        ForeignKey(
            entity = Vivienda::class,
            parentColumns = ["id"],
            childColumns = ["viviendaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    // Este índice ÚNICO asegura la relación 1:1
    indices = [Index(value = ["viviendaId"], unique = true)]
)
data class Certificado(
    @PrimaryKey(autoGenerate = true)
    val certificadoId: Int = 0,
    val eficienciaEnergetica: String, // Ej: "A", "G"
    val fechaCaducidad: String,
    val viviendaId: Int // FK hacia Vivienda
)