package com.example.tg_navegacionpantallas.ui.screens.detalles

import android.widget.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//Cantidad total y precio
class CarritoScreen (navController: NavController){// pasarle el componente de navegación
    fun infoCarrito(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
            ) {
                Button(
                    onClick = {
                        navController.navigate("home")// esto envía a la pantalla home, no solo va atrás
                    }
                )
            }

        }
    }
}