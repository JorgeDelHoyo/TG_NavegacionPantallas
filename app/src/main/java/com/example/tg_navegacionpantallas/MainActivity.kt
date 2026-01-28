package com.example.tg_navegacionpantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue // Necesario para 'by'
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tg_navegacionpantallas.data.repository.ViviendaProvider
import com.example.tg_navegacionpantallas.navigation.Destinos
import com.example.tg_navegacionpantallas.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Cargar JSON
        ViviendaProvider.cargarDesdeJson(this)

        setContent {
            // Si no tienes el Theme aún, usa MaterialTheme o Surface directo
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

    // Saber dónde estamos para ocultar barras en Bienvenida/Denegado
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Lista blanca de pantallas donde SÍ queremos barras
    val showBars = currentRoute in listOf(
        Destinos.Inicio.ruta,
        Destinos.Carrito.ruta,
        "detalles/{id}" // Detalles también suele llevar TopBar
    )

    Scaffold(
        topBar = {
            if (showBars) {
                CenterAlignedTopAppBar(
                    title = { Text("Inmobiliaria") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        },
        bottomBar = {
            if (showBars) {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Inicio") },
                        selected = currentRoute == Destinos.Inicio.ruta,
                        onClick = { navController.navigate(Destinos.Inicio.ruta) }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") },
                        label = { Text("Carrito") },
                        selected = currentRoute == Destinos.Carrito.ruta,
                        onClick = { navController.navigate(Destinos.Carrito.ruta) }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Importante: Pasar el padding para que el contenido no quede tapado
        Surface(modifier = Modifier.padding(innerPadding)) {
            // Pasamos el navController creado aquí al NavGraph
            NavGraph(navController = navController)
        }
    }
}