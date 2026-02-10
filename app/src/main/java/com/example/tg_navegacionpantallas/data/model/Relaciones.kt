package com.example.tg_navegacionpantallas.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

// Clase para obtener: 1 Vivienda + Sus Comodidades (N:M)
data class ViviendaConComodidades(
    @Embedded val vivienda: Vivienda,
    @Relation(
        parentColumn = "id",
        entityColumn = "comodidadId",
        associateBy = Junction(ViviendaComodidadCrossRef::class)
    )
    val comodidades: List<Comodidad>
)

// Clase para obtener: 1 Vivienda + Su Certificado (1:1)
data class ViviendaConCertificado(
    @Embedded val vivienda: Vivienda,
    @Relation(
        parentColumn = "id",
        entityColumn = "viviendaId"
    )
    val certificado: Certificado?
)