package com.example.tg_navegacionpantallas.ui.screens.detalles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.tg_navegacionpantallas.data.model.Vivienda

//Cantidad total y precio
@Composable
fun pantallaCarrito(navController: NavController, viviendasSeleccionadas: MutableList<Vivienda>){
   Column {
       LazyColumn {
           items(viviendasSeleccionadas, key = { it.id }) {
               Row() {
                   Text(text = it.modelo)
                   Text(text = it.precio.toString())
                   Text(text = it.cantidad.toString())
               }
           }
       }
   }
}