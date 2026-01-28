package com.example.tg_navegacionpantallas

import android.os.Bundle
import android.util.Log // Importante para poder imprimir en consola
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.example.tg_navegacionpantallas.data.repository.ViviendaProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // LLAMADA AL MÉTODO DE PRUEBA
        verificarCargaJson()

        setContent {
            // Solo para que la pantalla no salga negra
            Text(text = "Revisa el Logcat (pestaña abajo) para ver los datos.")
        }
    }

    // --- ESTE ES EL MÉTODO QUE PIDES ---
    private fun verificarCargaJson() {
        // 1. Ejecutamos la carga usando tu Provider
        ViviendaProvider.cargarDesdeJson(this)

        // 2. Comprobamos si la lista tiene datos
        val lista = ViviendaProvider.listaViviendas

        if (lista.isNotEmpty()) {
            Log.d("PRUEBA_JSON", "¡ÉXITO! Se han cargado ${lista.size} viviendas.")

            // 3. Imprimimos las 3 primeras para ver que los campos están bien
            lista.take(3).forEach { vivienda ->
                Log.d("PRUEBA_JSON", "--> Vivienda ID ${vivienda.id}: ${vivienda.modelo} - ${vivienda.precio}€")
            }
        } else {
            Log.e("PRUEBA_JSON", "ERROR: La lista está vacía. Revisa el nombre del archivo en assets.")
        }
    }
}