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
import com.example.ecoretosapp.data.TempData
import com.example.ecoretosapp.data.model.RetoPropuesto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRetoEstudianteScreen(
    volver: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val categorias = listOf(
        "Reciclaje",
        "Energía",
        "Agua",
        "Campus",
        "Transporte",
        "Consumo responsable"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFECFDF5), Color.White, Color(0xFFF7FEE7))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Proponer reto",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Envía una idea para que el administrador la revise",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
        }

        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del reto") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = categoria,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoría") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(18.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categorias.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    categoria = opcion
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(18.dp)
                )

                if (mensajeError.isNotBlank()) {
                    Text(
                        text = mensajeError,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable {
                            if (nombre.isBlank() || categoria.isBlank() || descripcion.isBlank()) {
                                mensajeError = "Completa todos los campos antes de enviar"
                                return@clickable
                            }

                            if (nombre.trim().length < 3) {
                                mensajeError = "El nombre debe tener al menos 3 caracteres"
                                return@clickable
                            }

                            if (descripcion.trim().length < 10) {
                                mensajeError = "La descripción debe tener al menos 10 caracteres"
                                return@clickable
                            }

                            TempData.retosPropuestos.add(
                                RetoPropuesto(
                                    id = TempData.retosPropuestos.size + 1,
                                    nombre = nombre.trim(),
                                    categoria = categoria,
                                    descripcion = descripcion.trim(),
                                    creadoPor = "Estudiante"
                                )
                            )

                            mensajeError = "Propuesta enviada para revisión"

                            nombre = ""
                            categoria = ""
                            descripcion = ""
                        }
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF10B981),
                                    Color(0xFF84CC16)
                                )
                            ),
                            shape = RoundedCornerShape(18.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Enviar propuesta",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                OutlinedButton(
                    onClick = volver,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Volver")
                }
            }
        }
    }
}