package com.example.ecoretosapp.data.model

data class CrearRetoRequest(
    val titulo: String,
    val descripcion: String,
    val puntos: Int,
    val dificultad: String,
    val tipoValidacion: String,
    val creadoPor: Int,
    val duracionHoras: Int
)