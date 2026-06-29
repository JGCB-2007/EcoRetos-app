package com.example.ecoretosapp.data.model

data class AdminDashboardResponse(
    val retosActivos: Long,
    val evidenciasPendientes: Long,
    val insigniasActivas: Long,
    val usuariosRegistrados: Long,
    val propuestasPendientes: Long
)