package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.ecoretosapp.viewmodel.AdminInsigniaViewModel


@Composable
fun CrearInsigniaScreen(
    volver: () -> Unit,
    viewModel: AdminInsigniaViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var puntos by remember { mutableStateOf("") }
    var icono by remember { mutableStateOf("🏆") }
    var mensaje by remember { mutableStateOf("") }
    val mensajeApi by viewModel.mensaje.collectAsState()
    val errorApi by viewModel.error.collectAsState()
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF10B981),
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = Color(0xFF10B981),
                cursorColor = Color(0xFF10B981)
            ),
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF10B981),
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = Color(0xFF10B981),
                cursorColor = Color(0xFF10B981)
            ),
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF10B981),
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = Color(0xFF10B981),
                cursorColor = Color(0xFF10B981)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Selecciona un icono",
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
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
        if (mensajeApi != null) {
            Text(
                text = mensajeApi ?: "",
                color = Color(0xFF15803D),
                fontWeight = FontWeight.Bold
            )
        }

        if (errorApi != null) {
            Text(
                text = errorApi ?: "",
                color = MaterialTheme.colorScheme.error,
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

                val puntosInt = puntos.toIntOrNull()

                if (puntosInt == null || puntosInt <= 0) {
                    mensaje = "Los puntos deben ser un número válido mayor que 0"
                    return@Button
                }

                viewModel.crearInsignia(
                    nombre = nombre.trim(),
                    descripcion = descripcion.trim(),
                    puntosMinimos = puntosInt,
                    iconoUrl = icono
                )

                mensaje = ""
                nombre = ""
                descripcion = ""
                puntos = ""
                icono = "🏆"
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF10B981)
            )
        ) {
                Text(
                    text = "Guardar insignia",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Volver",
                color = Color(0xFF10B981),
                fontWeight = FontWeight.Bold
            )
        }
    }
}