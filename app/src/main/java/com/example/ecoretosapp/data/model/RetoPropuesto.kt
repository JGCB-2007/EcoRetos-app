package com.example.ecoretosapp.data.model

data class RetoPropuesto(
    val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val descripcion: String,
    val creadoPor: String,
    val estado: String = "Pendiente"
)