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

        // DETALLES
        composable(
            route = "detalles/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DetallesScreen(
                idVivienda = id,
                onComprar = { casa ->
                    carritoDeCompras.add(casa)
                    navController.popBackStack()
                },
                onVolver = { navController.popBackStack() }
            )
        }

        // DENEGADO
        composable(Destinos.Denegado.ruta) {
            DenegadoScreen(onVolver = { navController.popBackStack() })
        }

        // CARRITO (Placeholder para que no falle el botón del menú)
        composable(Destinos.Carrito.ruta) {
            // CarritoScreen(...)
            androidx.compose.material3.Text("Pantalla de Carrito (En construcción)")
        }
    }
}