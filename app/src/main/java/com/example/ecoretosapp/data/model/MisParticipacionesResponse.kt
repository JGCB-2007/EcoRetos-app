package com.example.ecoretosapp.data.model

data class MisParticipacionesResponse(
    val idParticipacion: Int,
    val idUsuario: Int,
    val idReto: Int,
    val tituloReto: String,
    val estado: String,
    val fechaAceptacion: String,
    val mensaje: String? = null
)