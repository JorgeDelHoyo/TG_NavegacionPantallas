package com.example.tg_navegacionpantallas.ui.screens.inicio

import androidx.compose.runtime.Composable
import com.example.tg_navegacionpantallas.data.repository.ViviendaProvider
// ... asegúrate de tener los imports de UI (Column, Text, etc)

@Composable
fun InicioScreen(
    onViviendaClick: (Int) -> Unit, // Recibe un ID
    onIrCarrito: () -> Unit         // Acción sin parámetros
) {
    // Aquí va el código que te pasé anteriormente con el LazyColumn
    // Asegúrate de usar 'onViviendaClick(vivienda.id)' al pulsar una tarjeta
    // y 'onIrCarrito()' al pulsar el botón de carrito.

    // ... (El resto del código de la lista y el filtro que hicimos antes)
}