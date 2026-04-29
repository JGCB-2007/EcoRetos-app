package com.example.ecoretosapp.data.model

data class Reto(
    val idReto: Int,
    val titulo: String,
    val descripcion: String,
    val puntos: Int,
    val dificultad: String,
    val tipoValidacion: String
)