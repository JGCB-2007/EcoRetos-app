package com.example.ecoretosapp.data.model

data class CrearInsigniaRequest(
    val nombre: String,
    val descripcion: String,
    val puntosMinimos: Int,
    val iconoUrl: String?
)