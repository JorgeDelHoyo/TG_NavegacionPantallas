package com.example.tg_navegacionpantallas.data.repository

import android.content.Context
import com.example.tg_navegacionpantallas.data.model.Vivienda
import kotlinx.serialization.json.Json

object ViviendaProvider {

    // Variable donde las pantallas leen datos
    var listaViviendas: List<Vivienda> = emptyList()

    // Configuración para que no falle por errores tontos de formato
    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    fun cargarDesdeJson(context: Context) {
        try {
            // Leemos el archivo
            val jsonString = context.assets.open("viviendas.json")
                .bufferedReader()
                .use { it.readText() }

            // Convertimos de texto plano a lista de objetos
            listaViviendas = jsonConfig.decodeFromString<List<Vivienda>>(jsonString)

            // Log de depuración para verificar la carga
            println("DEBUG: Se han cargado ${listaViviendas.size} viviendas")

        } catch (e: Exception) {
            // Si hay un error se captura el error
            android.util.Log.e("DEBUG_JSON", "Error leyendo JSON: ${e.message}")
            e.printStackTrace()

            // Lista vacía por seguridad
            listaViviendas = emptyList()
        }
    }
}