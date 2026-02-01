package com.example.tg_navegacionpantallas.navigation

sealed class Destinos(val ruta: String) {
    // Pantalla de Bienvenida (Edad)
    object Bienvenida : Destinos("bienvenida")

    // Pantalla Principal (Lista de viviendas)
    object Inicio : Destinos("inicio")

    // Pantalla de Acceso Denegado (Menores de edad)
    object Denegado : Destinos("denegado")

    // Pantalla del Carrito de Compra
    object Carrito : Destinos("carrito")

    // Pantalla de Detalles(recibe un ID)
    object Detalles : Destinos("detalles/{id}") {
        // Función de ayuda para crear la ruta fácilmente: Destinos.Detalles.crearRuta(5)
        fun crearRuta(id: Int) = "detalles/$id"
    }
}