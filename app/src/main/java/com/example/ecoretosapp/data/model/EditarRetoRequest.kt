package com.example.ecoretosapp.data.model

data class EditarRetoRequest(
    val titulo: String,
    val descripcion: String,
    val puntos: Int,
    val dificultad: String,
    val tipoValidacion: String,
    val duracionHoras: Int
)