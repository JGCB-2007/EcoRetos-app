package com.example.ecoretosapp.data.model

data class ImpactoUsuarioResponse(
    val puntosTotales: Int,
    val retosCompletados: Long,
    val retosCompletadosSemana: Long,
    val rachaDias: Int,
    val posicionRanking: Int
)