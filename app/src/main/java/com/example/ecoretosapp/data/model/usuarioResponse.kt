package com.example.ecoretosapp.data.model

data class UsuarioResponse(
    val idUsuario: Int,
    val cif: String,
    val nombreCompleto: String,
    val correoInstitucional: String?,
    val rol: String,
    val puntosTotales: Int
)