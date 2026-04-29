package com.example.ecoretosapp.data.model

data class LoginResponse(
    val idUsuario: Int,
    val cif: String,
    val nombreCompleto: String,
    val rol: String,
    val mensaje: String
)