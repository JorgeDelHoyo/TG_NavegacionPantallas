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
// import com.example.tg_navegacionpantallas.ui.screens.carrito.CarritoScreen (Cuando la tengas)

@Composable
fun NavGraph(navController: NavHostController) { // <--- CAMBIO CLAVE: Recibe el controller

    val carritoDeCompras = remember { mutableStateListOf<Vivienda>() }

    NavHost(
        navController = navController,
        startDestination = Destinos.Bienvenida.ruta
    ) {
        // BIENVENIDA
        composable(Destinos.Bienvenida.ruta) {
            BienvenidaScreen(
                onEntrar = { navController.navigate(Destinos.Inicio.ruta) },
                onDenegado = { navController.navigate(Destinos.Denegado.ruta) }
            )
        }

        // RUTA INICIO
        composable(Destinos.Inicio.ruta) {
            InicioScreen(
                onNavegarDetalles = { idVivienda ->
                    // Requisito: Botón confirmación -> Detalles
                    navController.navigate("detalles/$idVivienda")
                },
                onVolverAtras = {
                    // Requisito: Botón vuelta atrás -> Pantalla anterior (Bienvenida)
                    navController.popBackStack()
                }
            )
        }

        // RUTA DETALLES
        composable(
            route = "detalles/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            DetallesScreen(
                idVivienda = id,
                onVolver = { navController.popBackStack() }, // Botón volver funciona aquí
                onComprar = { vivienda, cantidad ->

                    // 1. Creamos una copia de la vivienda con la cantidad correcta
                    // (Importante: tu data class Vivienda debe tener val cantidad: Int)
                    val viviendaConCantidad = vivienda.copy(cantidad = cantidad)

                    // 2. La añadimos al carrito
                    carritoDeCompras.add(viviendaConCantidad)

                    // 3. Confirmación en consola y vuelta atrás
                    println("Añadido al carrito: ${vivienda.modelo} (x$cantidad)")
                    navController.popBackStack()
                }
            )
        }

        // DENEGADO
        composable(Destinos.Denegado.ruta) {
            DenegadoScreen(onVolver = { navController.popBackStack() })
        }

        // RUTA CARRITO (ACTUALIZAR ESTO)
        composable(Destinos.Carrito.ruta) {
            CarritoScreen(
                listaViviendas = carritoDeCompras, // Pasamos la lista llena
                onFinalizarCompra = {
                    // Acción: Vaciar el carrito
                    carritoDeCompras.clear()

                    // Opcional: Navegar a inicio tras comprar
                    navController.navigate(Destinos.Inicio.ruta) {
                        popUpTo(Destinos.Inicio.ruta) { inclusive = true }
                    }
                }
            )
        }
    }
}