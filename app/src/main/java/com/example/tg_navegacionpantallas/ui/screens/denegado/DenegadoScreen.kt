package com.example.tg_navegacionpantallas.ui.screens.denegado // <--- Crea esta carpeta si no existe

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DenegadoScreen(
    onVolver: () -> Unit // Callback para volver al principio
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("ACCESO DENEGADO", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Debes ser mayor de 18 años según la ley vigente.")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onVolver) {
            Text("Volver a Bienvenida")
        }
    }
}