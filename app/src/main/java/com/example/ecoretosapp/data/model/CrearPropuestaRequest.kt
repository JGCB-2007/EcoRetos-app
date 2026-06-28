package com.example.ecoretosapp.data.model

data class CrearPropuestaRequest(
    val idUsuario: Int,
    val titulo: String,
    val descripcion: String,
    val categoria: String
)