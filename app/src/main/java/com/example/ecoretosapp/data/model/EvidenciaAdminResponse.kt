package com.example.ecoretosapp.data.model

data class EvidenciaAdminResponse (
    val idEvidencia: Int,
    val idParticipacion: Int,
    val nombreUsuario: String,
    val tituloReto: String,
    val urlImagen: String,
    val estadoValidacion: String,
    val fechaSubida: String
)