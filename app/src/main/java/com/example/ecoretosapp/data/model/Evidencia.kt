package com.example.ecoretosapp.data.model

data class Evidencia(
    val id: Int = 0,
    val idReto: Int,
    val nombreReto: String,
    val comentario: String,
    val imagenUri: String = "",
    val enviadoPor: String = "Estudiante",
    val estado: String = "Pendiente"
)