package com.example.tg_navegacionpantallas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tg_navegacionpantallas.data.model.Vivienda
import com.example.tg_navegacionpantallas.ui.theme.TG_NavegacionPantallasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Primero llenamos la lista
        ViviendaProvider.cargarDesdeJson(this)

        setContent {
            // Luego mostramos la pantalla usando esa lista
            Text("revisa Logcat")
        }
    }
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
    }}

@Composable
fun PantallaPruebaDatos(viviendas: List<Vivienda>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Viviendas cargadas desde JSON",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Si la función cargarDesdeJson falló o el JSON está vacío:
        if (viviendas.isEmpty()) {
            Text("Error: No hay datos. Revisa 'assets/viviendas.json'", color = Color.Red)
        } else {
            // Requisito: Lista de items a seleccionar
            LazyColumn {
                items(viviendas) { vivienda ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "ID: ${vivienda.id} - ${vivienda.modelo}", style = MaterialTheme.typography.titleMedium)
                            Text(text = "${vivienda.metros} m² - ${vivienda.precio} €", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }

}
