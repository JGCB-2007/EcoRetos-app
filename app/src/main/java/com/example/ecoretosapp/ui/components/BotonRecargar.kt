package com.example.ecoretosapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BotonRecargar(
    onRecargar: () -> Unit
) {
    IconButton(
        onClick = onRecargar
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Recargar pantalla",
            tint = Color(0xFF047857)
        )
    }
}