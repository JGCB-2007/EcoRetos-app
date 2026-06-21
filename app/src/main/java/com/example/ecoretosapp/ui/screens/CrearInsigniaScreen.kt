package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CrearInsigniaScreen(
    volver: () -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var puntos by remember { mutableStateOf("") }
    var icono by remember { mutableStateOf("🏆") }
    var mensaje by remember { mutableStateOf("") }

    val iconos = listOf(
        "🏆",
        "🌱",
        "♻️",
        "🌍",
        "💧",
        "⚡",
        "🌳",
        "🚲"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFECFDF5),
                        Color.White,
                        Color(0xFFF7FEE7)
                    )
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Crear insignia",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
            },
            label = {
                Text("Descripción")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = puntos,
            onValueChange = {
                puntos = it
            },
            label = {
                Text("Puntos requeridos")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Selecciona un icono",
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            iconos.forEach { emoji ->
                Card(
                    modifier = Modifier.clickable {
                        icono = emoji
                    }
                ) {
                    Text(
                        text = emoji,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 26.sp
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = icono,
                    fontSize = 50.sp
                )

                Text(
                    text = nombre.ifBlank { "Nombre de insignia" },
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = descripcion.ifBlank { "Descripción" }
                )
            }
        }

        if (mensaje.isNotBlank()) {
            Text(
                text = mensaje,
                color = Color(0xFF15803D),
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {

                if (
                    nombre.isBlank() ||
                    descripcion.isBlank() ||
                    puntos.isBlank()
                ) {
                    mensaje = "Completa todos los campos"
                    return@Button
                }

                mensaje = "Insignia creada correctamente"

                nombre = ""
                descripcion = ""
                puntos = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar insignia")
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}