package com.example.tg_navegacionpantallas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController // Importante
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tg_navegacionpantallas.data.model.Vivienda
import com.example.tg_navegacionpantallas.ui.screens.bienvenida.BienvenidaScreen
import com.example.tg_navegacionpantallas.ui.screens.carrito.CarritoScreen
import com.example.tg_navegacionpantallas.ui.screens.denegado.DenegadoScreen
import com.example.tg_navegacionpantallas.ui.screens.detalles.DetallesScreen
import com.example.tg_navegacionpantallas.ui.screens.inicio.InicioScreen
import com.example.tg_navegacionpantallas.ui.screens.carrito.CarritoScreen

@Composable
fun NavGraph(navController: NavHostController) {
    // Recbiir navController desde el Main en lugar de crearlo aquí para misma lógica con bottombar

    // Elevación de estado porque Detalles escribe en carrito y carrito necesita leerlo
    val carritoDeCompras = remember { mutableStateListOf<Vivienda>() }

    NavHost(
        navController = navController,
        startDestination = Destinos.Bienvenida.ruta
    ) {
        // 1. Bienvenida
        composable(Destinos.Bienvenida.ruta) {
            BienvenidaScreen(
                onEntrar = { navController.navigate(Destinos.Inicio.ruta) },
                onDenegado = { navController.navigate(Destinos.Denegado.ruta) }
            )
        }

        // 2. Inicio
        composable(Destinos.Inicio.ruta) {
            InicioScreen(
                onNavegarDetalles = { idVivienda ->
                    // Ruta con parámetros
                    navController.navigate("detalles/$idVivienda")
                },
                onVolverAtras = {
                    //Volver a la pantalla anterior
                    navController.popBackStack()
                }
            )
        }

        // 3. Detalles
        composable(
            route = "detalles/{id}", // Variable del inicio
            arguments = listOf(navArgument("id") { type = NavType.IntType }) // Tipo entero
        ) { backStackEntry ->
            // Recuperar ID de argumentos de navegación
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            DetallesScreen(
                idVivienda = id,
                onVolver = { navController.popBackStack() }, // Botón volver

                // Lógica del carrito
                onComprar = { vivienda, cantidad ->

                    // Crear una copia de la vivienda con cantidad correcta
                    val viviendaConCantidad = vivienda.copy(cantidad = cantidad)

                    // Añadirla al carrito
                    carritoDeCompras.add(viviendaConCantidad)

                    // Confirmamos y vuelta atrás ( seguir comprando )
                    println("Añadido al carrito: ${vivienda.modelo} (x$cantidad)")
                    navController.popBackStack()
                }
            )
        }

        // 4. Denegado
        composable(Destinos.Denegado.ruta) {
            DenegadoScreen(onVolver = { navController.popBackStack() })
        }

        // 5. Carrito
        composable(Destinos.Carrito.ruta) {
            CarritoScreen(
                listaViviendas = carritoDeCompras, // Lista llena para que la pinte
                onFinalizarCompra = {
                    // Limpiar la lista
                    carritoDeCompras.clear()

                    // Navegar a inicio tras comprar y borrar historial
                    navController.navigate(Destinos.Inicio.ruta) {
                        popUpTo(Destinos.Inicio.ruta) { inclusive = true }
                    }
                }
            )
        }
    }
}