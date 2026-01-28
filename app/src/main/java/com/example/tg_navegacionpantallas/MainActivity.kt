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
import com.example.tg_navegacionpantallas.data.repository.ViviendaProvider
import com.example.tg_navegacionpantallas.navigation.Destinos
import com.example.tg_navegacionpantallas.navigation.NavGraph
//import com.example.tg_navegacionpantallas.ui.theme.Tg_NavegacionPantallasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViviendaProvider.cargarDesdeJson(this) // Carga de datos

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

    // Detectamos la ruta actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // --- CONFIGURACIÓN DE VISIBILIDAD ---

    // 1. ¿Dónde mostramos las barras (Top y Bottom)?
    // En todas partes MENOS en Bienvenida y Denegado
    val mostrarBarras = currentRoute != Destinos.Bienvenida.ruta &&
            currentRoute != Destinos.Denegado.ruta

    // 2. ¿Cuándo mostramos la flecha de volver?
    // SIEMPRE que haya barras (es decir, en Inicio, Detalles y Carrito)
    // - En Inicio: Volverá a Bienvenida.
    // - En Detalles/Carrito: Volverá a Inicio.
    val mostrarFlechaAtras = mostrarBarras

    Scaffold(
        // --- TOP BAR (Con botón de retorno SIEMPRE) ---
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

        // --- BOTTOM BAR (Navegación entre Home y Carrito) ---
        bottomBar = {
            if (mostrarBarras) {
                NavigationBar {
                    // Botón INICIO
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Inicio") },
                        // Se marca si estamos en Inicio O en Detalles (para saber que venimos de ahí)
                        selected = currentRoute == Destinos.Inicio.ruta || currentRoute?.startsWith("detalles") == true,
                        onClick = {
                            // Navegar a inicio limpiando la pila para evitar bucles
                            navController.navigate(Destinos.Inicio.ruta) {
                                popUpTo(Destinos.Inicio.ruta) { inclusive = true }
                            }
                        }
                    )

                    // Botón CARRITO
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
        // Contenido principal con padding para no chocar con las barras
        Surface(modifier = Modifier.padding(innerPadding)) {
            NavGraph(navController = navController)
        }
    }
}