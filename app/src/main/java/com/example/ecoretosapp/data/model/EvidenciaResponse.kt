package com.example.ecoretosapp.data.model

data class EvidenciaResponse(
    val idEvidencia: Int,
    val idParticipacion: Int,
    val urlImagen: String,
    val estadoValidacion: String,
    val mensaje: String
)