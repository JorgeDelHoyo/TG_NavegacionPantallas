package com.example.tg_navegacionpantallas.ui.screens.detalles

import androidx.compose.runtime.Composable
import com.example.tg_navegacionpantallas.data.model.Vivienda
// ... resto de imports

@Composable
fun DetallesScreen(
    idVivienda: Int,               // El dato obligatorio
    onComprar: (Vivienda) -> Unit, // Acción de comprar
    onVolver: () -> Unit           // Acción de volver
) {
    // ... (El código que te pasé para mostrar la imagen y descripción)
}