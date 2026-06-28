package com.example.ecoretosapp.data.model

data class InsigniaResponse(
    val idInsignia: Int,
    val nombre: String,
    val descripcion: String,
    val puntosMinimos: Int,
    val iconoUrl: String?,
    val activa: Boolean? = null
)