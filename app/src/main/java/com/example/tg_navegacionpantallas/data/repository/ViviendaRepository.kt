package com.example.tg_navegacionpantallas.data.repository

import android.content.Context
import com.example.tg_navegacionpantallas.data.local.dao.ViviendaDao
import com.example.tg_navegacionpantallas.data.model.Vivienda
import kotlinx.coroutines.flow.Flow

class ViviendaRepository(
    private val viviendaDao: ViviendaDao,
    private val context: Context // Necesitamos contexto para pasarle al Provider
) {

    // Esta es la "Fuente de Verdad". La UI observará esto.
    val todasLasViviendas: Flow<List<Vivienda>> = viviendaDao.getAllViviendas()

    // Lógica de inicialización (Seed)
    suspend fun inicializarBaseDeDatos() {
        val cantidad = viviendaDao.contarViviendas()

        if (cantidad == 0) {
            // 1. La base de datos está vacía, usamos el Provider antiguo
            val datosDelJson = ViviendaProvider.cargarDesdeJson(context)

            // 2. Guardamos en la Base de Datos
            if (datosDelJson.isNotEmpty()) {
                viviendaDao.insertarTodas(datosDelJson)
            }
        }
        // Si cantidad > 0, no hacemos nada, ya tenemos datos persistentes.
    }

    // --- Métodos CRUD básicos para la UI ---

    suspend fun insertar(vivienda: Vivienda) {
        viviendaDao.insertarVivienda(vivienda)
    }

    suspend fun eliminar(vivienda: Vivienda) {
        viviendaDao.eliminarVivienda(vivienda)
    }

    suspend fun actualizar(vivienda: Vivienda) {
        viviendaDao.actualizarVivienda(vivienda)
    }
}