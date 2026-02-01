package com.example.tg_navegacionpantallas.ui.screens.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import com.example.tg_navegacionpantallas.data.model.Vivienda
import com.example.tg_navegacionpantallas.data.repository.ViviendaProvider

@Composable
fun InicioScreen(
    onNavegarDetalles: (Int) -> Unit, // Acción del botón confirmar
    onVolverAtras: () -> Unit         // Acción del botón volver
) {
    // Variable para lo que escribe en el filtro el usuario
    var textoFiltro by remember { mutableStateOf("") }
    // Variable para guardar el objeto que el usuario ha seleccionado pero no confirmado
    var viviendaSeleccionada by remember { mutableStateOf<Vivienda?>(null) }

    // Cuando cambia textoFiltro, se recalcula y cambia
    val listaFiltrada = ViviendaProvider.listaViviendas.filter {
        it.modelo.contains(textoFiltro, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Filtro
        OutlinedTextField(
            value = textoFiltro,
            onValueChange = {
                textoFiltro = it
                viviendaSeleccionada = null // Deseleccionar al filtrar
            },
            label = { Text("Buscar por modelo...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de items filtrados
        Text("Resultados: ${listaFiltrada.size}", style = MaterialTheme.typography.labelLarge)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listaFiltrada) { vivienda ->
                // Comprobar que la vivienda es la seleccionada actualmente
                val esSeleccionada = vivienda == viviendaSeleccionada

                ItemSeleccionable(
                    vivienda = vivienda,
                    seleccionada = esSeleccionada,
                    onClick = { viviendaSeleccionada = vivienda } // Solo seleccionamos, no navegamos
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Vivienda Seleccionada:", style = MaterialTheme.typography.labelMedium)
            // Mostrar nombre o placeholder si es null
            Text(
                text = viviendaSeleccionada?.modelo ?: "Ninguna seleccionada",
                style = MaterialTheme.typography.titleMedium,
                color = if (viviendaSeleccionada != null) MaterialTheme.colorScheme.primary else Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botones
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            // Botón de Confirmación -> Hacia Detalles
            Button(
                onClick = {
                    // Solo navega si hay algo seleccionado
                    viviendaSeleccionada?.let { onNavegarDetalles(it.id) }
                },
                enabled = viviendaSeleccionada != null, // Desactivado si no seleccionas
                modifier = Modifier.weight(1f)
            ) {
                Text("Ver Detalles")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Check, contentDescription = "Check")
            }
        }

        // Espacio para que la BottomBar no tape contenido
        Spacer(modifier = Modifier.height(60.dp))
    }
}

/**
 * Componente auxiliar para pintar la fila
 */
@Composable
fun ItemSeleccionable(
    vivienda: Vivienda,
    seleccionada: Boolean,
    onClick: () -> Unit
) {
    // Cambiamos el color de fondo si está seleccionado
    val backgroundColor = if (seleccionada) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val borderColor = if (seleccionada) MaterialTheme.colorScheme.primary else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Al hacer click, marcamos la variable, no navegamos
            .border(2.dp, borderColor, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(if (seleccionada) 8.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Miniatura de la imagen
            val context = LocalContext.current
            val resourceId = remember(vivienda.imagen) {
                context.resources.getIdentifier(vivienda.imagen, "drawable", context.packageName)
            }

            if (resourceId != 0) {
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(60.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Textos de la fila
            Column {
                Text(text = vivienda.modelo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "${vivienda.precio} €", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}