package com.example.ecoretosapp.data.model

/**
 * Modelo de datos que representa un reto ecológico dentro de la aplicación.
 * Contiene la información necesaria para mostrar los retos disponibles al usuario.
 */

data class Reto(
    val idReto: Int,
    val titulo: String,
    val descripcion: String,
    val puntos: Int,
    val dificultad: String,
    val tipoValidacion: String
)