package com.example.tg_navegacionpantallas.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity(
    tableName = "viviendas",
    foreignKeys = [
        ForeignKey(
            entity = Agente::class,
            parentColumns = ["agenteId"],
            childColumns = ["agenteId"],
            onDelete = ForeignKey.CASCADE // Si borras al agente, se borran sus casas
        )
    ]
)
data class Vivienda(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Clave primaria de la BD

    val titulo: String,
    val precio: Double,
    val descripcion: String,
    val imagen: String, // URL o nombre del recurso
    // ... otros campos que tengas en tu JSON ...

    // Relación 1:N (Una vivienda pertenece a un Agente)
    // Le ponemos un valor por defecto para que no falle al leer tu JSON antiguo
    val agenteId: Int = 1
)