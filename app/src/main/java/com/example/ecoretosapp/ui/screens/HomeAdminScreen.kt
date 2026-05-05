package com.example.ecoretosapp.ui.screens

/**
 * Pantalla principal del administrador.
 * Muestra las opciones disponibles para gestionar retos,
 * usuarios u otras funciones administrativas de la aplicación.
 */

import androidx.compose.runtime.Composable
import com.example.ecoretosapp.navigation.AdminHome

@Composable
fun HomeAdminScreen(
    onLogout: () -> Unit
) {
    AdminHome(
        onLogout = onLogout
    )
}