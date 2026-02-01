package com.example.tg_navegacionpantallas.ui.screens.detalles

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tg_navegacionpantallas.data.model.Vivienda
import com.example.tg_navegacionpantallas.data.repository.ViviendaProvider

@Composable
fun DetallesScreen(
    idVivienda: Int, // ID de la vivienda
    onComprar: (Vivienda, Int) -> Unit,
    onVolver: () -> Unit // Se mantiene por compatibilidad, aunque lo usa la TopBar
) {
    // 1. Buscamos la vivienda
    // Sin remember buscaría más de una vez cada que se escribe
    val vivienda = remember(idVivienda) {
        ViviendaProvider.listaViviendas.find { it.id == idVivienda }
    }

    // Estado para la cantidad
    var cantidadInput by remember { mutableStateOf("1") }

    if (vivienda != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Scroll
        ) {

            // Imagen dinámica
            val context = LocalContext.current
            val resourceId = remember(vivienda.imagen) {
                context.resources.getIdentifier(
                    vivienda.imagen,
                    "drawable",
                    context.packageName
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                // Si encuentra la imagen la pinta sino muestra el icono de la casita
                if (resourceId != 0) {
                    Image(
                        painter = painterResource(id = resourceId),
                        contentDescription = vivienda.modelo,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(50.dp), tint = Color.Gray)
                        Text("Imagen no disponible", color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Titulo y precio de la vivienda
            Text(
                text = vivienda.modelo,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${vivienda.precio} €",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Detalles de la vivienda
            Text("Características:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("• Superficie: ${vivienda.metros} m²")
            Text("• Descripción: ${vivienda.descripcion}")

            Spacer(modifier = Modifier.height(24.dp))

            // Inputa para la cantidad
            Text("Indicar cantidad:", style = MaterialTheme.typography.labelLarge)

            OutlinedTextField(
                value = cantidadInput,
                onValueChange = {
                    // Validación numérica solo si lo introducido son digitos
                    if (it.all { char -> char.isDigit() }) {
                        cantidadInput = it
                    }
                },
                label = { Text("Unidades") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- BOTÓN COMPRAR ---
            Button(
                onClick = {
                    val cantidadFinal = cantidadInput.toIntOrNull() ?: 1
                    if (cantidadFinal > 0) {
                        onComprar(vivienda, cantidadFinal)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                // Deshabilitar el botón si el campo está vacio o es 0
                enabled = cantidadInput.isNotEmpty() && cantidadInput != "0"
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Añadir al Carrito")
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    } else {
        // Error si no encuentra ID
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: Vivienda no encontrada")
        }
    }
}