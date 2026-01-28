package com.example.tg_navegacionpantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface // Opcional: Para tener un fondo blanco base
import com.example.tg_navegacionpantallas.data.repository.ViviendaProvider
import com.example.tg_navegacionpantallas.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. LÓGICA: Cargamos el JSON en memoria
        ViviendaProvider.cargarDesdeJson(this)

        // 2. INTERFAZ: Lanzamos la navegación "a pelo" (sin estilos personalizados)
        setContent {
            // He puesto un Surface simple para asegurar que el fondo sea opaco
            // y no se vea raro, pero usa los colores por defecto de Android.
            Surface {
                NavGraph()
            }
        }
    }
}