package com.example.tg_navegacionpantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tg_navegacionpantallas.data.db.AppDatabase
import com.example.tg_navegacionpantallas.data.repository.ViviendaProvider
import com.example.tg_navegacionpantallas.navigation.Destinos
import com.example.tg_navegacionpantallas.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // ------------------------------------------------------------------
        // ZONA DE PRUEBA DE BASE DE DATOS (INICIO)
        // ------------------------------------------------------------------

        // Instanciamos la base de datos
        val db = AppDatabase.getDatabase(this)

        // Lanzamos un hilo secundario (Thread) porque no se puede tocar la BD en el hilo principal
        Thread {
            // Esta línea fuerza a la BD a abrirse y crear las tablas si no existen
            println("DEBUG_DB: Intentando crear la base de datos...")
            db.openHelper.readableDatabase
            println("DEBUG_DB: ¡Base de datos y tablas creadas con éxito!")
        }.start()

        // ------------------------------------------------------------------
        // ZONA DE PRUEBA DE BASE DE DATOS (FIN)
        // ------------------------------------------------------------------


        // Primero carga Datos (Tu código original del JSON)
        ViviendaProvider.cargarDesdeJson(this)

        super.onCreate(savedInstanceState)
        // Después se muestra
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    // Variable para detectar la pantalla actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Solo se muestra la topBar y la bottomBar si no es Bienvenida o Denegado
    val mostrarBarras = currentRoute != Destinos.Bienvenida.ruta &&
            currentRoute != Destinos.Denegado.ruta

    // Variable para mostrar las flechas de "volver" en las pantallas determinadas
    val mostrarFlechaAtras = mostrarBarras

    Scaffold(
        topBar = {
            if (mostrarBarras) {
                CenterAlignedTopAppBar(
                    title = { Text("Inmobiliaria") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    navigationIcon = {
                        if (mostrarFlechaAtras) {
                            IconButton(onClick = {
                                // Acción de volver atrás
                                navController.popBackStack()
                            }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                            }
                        }
                    }
                )
            }
        },

        bottomBar = {
            if (mostrarBarras) {
                NavigationBar {
                    // Botón Inicio
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Inicio") },
                        // Se marca si estamos en Inicio o Detalles
                        selected = currentRoute == Destinos.Inicio.ruta || currentRoute?.startsWith("detalles") == true,
                        onClick = {
                            // Navegar a inicio limpiando la pila para evitar bucles si pulsas mucho el botón
                            navController.navigate(Destinos.Inicio.ruta) {
                                popUpTo(Destinos.Inicio.ruta) { inclusive = true }
                            }
                        }
                    )
                    // Botón Carrito
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") },
                        label = { Text("Carrito") },
                        selected = currentRoute == Destinos.Carrito.ruta,
                        onClick = {
                            if (currentRoute != Destinos.Carrito.ruta) {
                                navController.navigate(Destinos.Carrito.ruta)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Padding para no cortar el contenido
        Surface(modifier = Modifier.padding(innerPadding)) {
            NavGraph(navController = navController)
        }
    }
}