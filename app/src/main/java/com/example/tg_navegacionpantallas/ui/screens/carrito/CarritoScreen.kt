package com.example.tg_navegacionpantallas.ui.screens.carrito

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tg_navegacionpantallas.data.model.Vivienda

@Composable
fun CarritoScreen(
    listaViviendas: List<Vivienda>, // Lista llena desde NavGraph
    onFinalizarCompra: () -> Unit // Callback para limpiar el carrito al finalizar la compra
) {
    // Variables que calculan el precio y la cantidad de items añadidos al carrito
    val cantidadTotalItems = remember(listaViviendas) { listaViviendas.sumOf { it.cantidad } }
    val precioTotal = remember(listaViviendas) { listaViviendas.sumOf { it.precio * it.cantidad } }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Resumen del Pedido", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta del item añadido al carrito con detalles
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Fila de cantidad
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Cantidad de ítems:", style = MaterialTheme.typography.bodyLarge)
                    Text("$cantidadTotalItems", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                // Fila de Precio Total
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Precio Total:", style = MaterialTheme.typography.bodyLarge)
                    Text("$precioTotal €", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de los detalles
        Text("Detalle:", style = MaterialTheme.typography.titleMedium)

        if (listaViviendas.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("El carrito está vacío") // Aviso si la lista está vacía
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(listaViviendas) { vivienda ->
                    ItemCarrito(vivienda)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para procesar la compra y limpiar la lista
        Button(
            onClick = onFinalizarCompra,
            modifier = Modifier.fillMaxWidth(),
            enabled = listaViviendas.isNotEmpty()
        ) {
            Text("Finalizar Compra")
        }
    }
}

@Composable
fun ItemCarrito(vivienda: Vivienda) {
    // Tarjeta simple para cada elemento
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen dinámica asociada la ID del JSON
            val context = LocalContext.current
            val resourceId = remember(vivienda.imagen) {
                context.resources.getIdentifier(vivienda.imagen, "drawable", context.packageName)
            }

            // Si no hay imagen muestra una casita
            if (resourceId != 0) {
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(50.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Datos del item
            Column {
                Text(vivienda.modelo, fontWeight = FontWeight.Bold)
                Text("${vivienda.cantidad} x ${vivienda.precio} €") // Cantidad x Precio/ud
            }
        }
    }
}