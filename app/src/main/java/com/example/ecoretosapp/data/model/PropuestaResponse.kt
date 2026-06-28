package com.example.ecoretosapp.data.model

data class PropuestaResponse(
    val idPropuesta: Int,
    val idUsuario: Int,
    val nombreUsuario: String,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val estado: String,
    val fechaPropuesta: String,
    val observacionAdmin: String?
)