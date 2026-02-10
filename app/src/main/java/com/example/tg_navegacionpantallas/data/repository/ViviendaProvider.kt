package com.example.tg_navegacionpantallas.data.repository

import android.content.Context
import com.example.tg_navegacionpantallas.data.model.Vivienda
import kotlinx.serialization.json.Json
import android.util.Log // Importante para el Log.e

object ViviendaProvider {

    // Configuración JSON (se mantiene igual)
    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    // CAMBIO: Ahora la función devuelve List<Vivienda>
    // Ya no guarda nada en una variable interna, solo lee y entrega.
    fun cargarDesdeJson(context: Context): List<Vivienda> {
        return try {
            val jsonString = context.assets.open("viviendas.json")
                .bufferedReader()
                .use { it.readText() }

            val lista = jsonConfig.decodeFromString<List<Vivienda>>(jsonString)

            Log.d("DEBUG", "Se han cargado ${lista.size} viviendas desde JSON")
            lista // Devolvemos la lista

        } catch (e: Exception) {
            Log.e("DEBUG_JSON", "Error leyendo JSON: ${e.message}")
            e.printStackTrace()
            emptyList() // Si falla, devolvemos lista vacía
        }
    }
}