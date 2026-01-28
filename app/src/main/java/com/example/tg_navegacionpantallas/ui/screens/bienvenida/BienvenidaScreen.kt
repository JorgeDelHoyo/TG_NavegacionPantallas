package com.example.tg_navegacionpantallas.ui.screens.bienvenida

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BienvenidaScreen(
    onEntrar: () -> Unit,    // Callback si es mayor de edad
    onDenegado: () -> Unit   // Callback si es menor
) {
    var edad by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido a Inmobiliaria")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Introduce tu edad") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val edadNum = edad.toIntOrNull() ?: 0
            if (edadNum >= 18) {
                onEntrar() // Navega a inicio
            } else {
                onDenegado() // Navega a pantalla de bloqueo
            }
        }) {
            Text("Entrar")
        }
    }
}