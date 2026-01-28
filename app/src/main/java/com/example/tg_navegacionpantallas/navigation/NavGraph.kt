package com.example.tg_navegacionpantallas.navigation

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.tg_navegacionpantallas.data.model.Vivienda
import com.example.tg_navegacionpantallas.ui.screens.bienvenida.BienvenidaScreen
import com.example.tg_navegacionpantallas.ui.screens.denegado.DenegadoScreen
import com.example.tg_navegacionpantallas.ui.screens.detalles.DetallesScreen
import com.example.tg_navegacionpantallas.ui.screens.inicio.InicioScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    // ESTADO DEL CARRITO: Aquí guardamos las casas compradas para que no se pierdan al navegar
    // (Importante: Asegúrate de importar 'mutableStateListOf' y 'remember')
    val carritoDeCompras = remember { mutableStateListOf<Vivienda>() }

    NavHost(
        navController = navController,
        startDestination = Destinos.Bienvenida.ruta
    ) {
        composable(Destinos.Bienvenida.ruta) {
        BienvenidaScreen(
            onEntrar = { navController.navigate(Destinos.Inicio.ruta) },
            onDenegado = { navController.navigate(Destinos.Denegado.ruta) }
        )
    }

        composable(Destinos.Denegado.ruta) {
            DenegadoScreen(
                onVolver = {
                    // Vuelve atrás borrando la pila para que no pueda volver a la pantalla de denegado
                    navController.popBackStack()
                }
            )
        }
        // --- RUTA 3: DETALLES ---
        composable(
            route = "detalles/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            DetallesScreen(
                idVivienda = id,
                onComprar = { vivienda ->
                    carritoDeCompras.add(vivienda) // Guardamos en el carrito
                    // Opcional: Navegar directo al carrito o volver atrás
                    navController.popBackStack()
                },
                onVolver = { navController.popBackStack() }
            )
        }

        // --- RUTA 4: CARRITO ---
        composable(Destinos.Carrito.ruta) {
            // Aquí llamarás a tu pantalla de Carrito pasándole la lista 'carritoDeCompras'
            // CarritoScreen(lista = carritoDeCompras, onVolver = { ... })
        }

        // --- RUTA 5: DENEGADO (Tuya) ---
        composable(Destinos.Denegado.ruta) {
            DenegadoScreen(
                onVolver = {
                    // Acción: Volver atrás (a la Bienvenida)
                    navController.popBackStack()
                }
            )
        }
    }
}