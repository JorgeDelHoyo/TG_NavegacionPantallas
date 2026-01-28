package com.example.tg_navegacionpantallas.ui.screens.bienvenida

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController


//Pide edad y verifica ley
@Composable
fun BienvenidaScreen(navController: NavController){

    var edadInput by remember { mutableStateOf("") }
    var resultadoMensaje by remember { mutableStateOf("") }
    //Booleano para saber si hay un error
    var esError by remember {mutableStateOf(false)}


    Column(){
        Text(
            text = "Bienvenid@, ingrese su edad para continuar."
        )
        TextField(
            value = edadInput,
            onValueChange = { edadInput = it},
            isError = esError // Si hay un error pone el borde rojo, ta guapo
        )

        Button(
            onClick = {
                /**
                 * Como el textField siempre da un String,
                 * se convierte el valor a Int, si no es un número
                 * se asigna un 0*/
                var edadSinEspacios = edadInput.trim()
                //Si se introduce un valor distinto a un Int, se convierte en null.
                var edadInt = edadSinEspacios.toIntOrNull()

                //Comprobación de texto
                if(edadInput.isEmpty()) {
                    resultadoMensaje = "El campo está vacío"
                    esError = true
                } else if (edadInt == null) {
                    resultadoMensaje = "Debe de introducir un número válido"
                    esError = true
                } else {
                    esError = false
                    if (edadInt >= 18) {
                        navController.navigate("inicio/InicioScreen.kt")
                    } else {
                        navController.navigate("/denegado/DenegadoScreen.kt")
                    }
                }
            } //End onClick
        ){
            Text("Entrar")
        } //End Button

        if (resultadoMensaje.isNotEmpty()) {
            Text (
                text = resultadoMensaje
            )
        }
    } //End Column

} //End BienvenidaScreen
