package com.example.ecoretosapp.data.model

data class RankingResponse(
    val posicion: Int,
    val idUsuario: Int,
    val nombreCompleto: String,
    val puntosTotales: Int,
    val cif: String
)