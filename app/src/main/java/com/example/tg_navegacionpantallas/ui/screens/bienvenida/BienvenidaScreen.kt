package com.example.tg_navegacionpantallas.ui.screens.bienvenida

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BienvenidaScreen(
    // Elevación de estado en ambas variables
    onEntrar:() -> Unit,
    onDenegado: () -> Unit
){
    // Estados para guardar lo que escribe el usuario y los mensajes de feedback
    var edadInput by remember { mutableStateOf("") }
    var resultadoMensaje by remember { mutableStateOf("") }

    // Variable para controlar el Textfield en rojo
    var esError by remember {mutableStateOf(false)}


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Bienvenid@, ingrese su edad para continuar."
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = edadInput,
            onValueChange = { edadInput = it},
            isError = esError // Si hay un error pone el borde rojo
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Solo mostramos texto error si hay algo escrito
        if (resultadoMensaje.isNotEmpty()) {
            Text (
                text = resultadoMensaje
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                /**
                 * Como el textField siempre da un String,
                 * se convierte el valor a Int, si no es un número
                 * se asigna un 0*/
                var edadSinEspacios = edadInput.trim()
                //Si se introduce un valor distinto a un Int, se convierte en null.
                var edadInt = edadSinEspacios.toIntOrNull()

                //Comprobar de texto
                if(edadInput.isEmpty()) {
                    resultadoMensaje = "Error!!. El campo está vacío"
                    esError = true
                } else if (edadInt == null) {
                    resultadoMensaje = "Error!!. Debe de introducir un número válido"
                    esError = true
                } else {
                    esError = false
                    if (edadInt >= 18) {
                        onEntrar()
                    } else {
                        onDenegado()
                    }
                }
            } //End onClick
        ){
            Text("Entrar")
        } //End Button
    } //End Column

} //End BienvenidaScreen