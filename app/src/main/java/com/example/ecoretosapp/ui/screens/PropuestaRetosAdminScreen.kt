package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecoretosapp.data.TempData
import com.example.ecoretosapp.data.model.RetoPropuesto

@Composable
fun PropuestasRetosAdminScreen(
    volver: () -> Unit,
    onAceptar: (RetoPropuesto, String) -> Unit,
    onRechazar: (RetoPropuesto) -> Unit
) {
    var propuestas by remember {
        mutableStateOf(TempData.retosPropuestos.toList())
    }

    LazyColumn(
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
        item {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Propuestas de retos",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Revisa, acepta o rechaza retos enviados por estudiantes",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        item {
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

        if (propuestas.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Text(
                        text = "No hay propuestas pendientes",
                        modifier = Modifier.padding(20.dp),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            items(propuestas) { propuesta ->
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = propuesta.nombre,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Categoría: ${propuesta.categoria}",
                            fontSize = 14.sp
                        )

                        Text(
                            text = "Propuesto por: ${propuesta.creadoPor}",
                            fontSize = 14.sp
                        )

                        AssistChip(
                            onClick = {},
                            label = {
                                Text(
                                    text = propuesta.estado,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )

                        Text(
                            text = propuesta.descripcion,
                            fontSize = 14.sp
                        )
                        var puntosAsignados by remember(propuesta.id) {
                            mutableStateOf("")
                        }

                        OutlinedTextField(
                            value = puntosAsignados,
                            onValueChange = {
                                puntosAsignados = it
                            },
                            label = {
                                Text("Puntos para este reto")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {

                                    if (puntosAsignados.isBlank()) {
                                        return@Button
                                    }

                                    if (puntosAsignados.toIntOrNull() == null) {
                                        return@Button
                                    }

                                    if (puntosAsignados.toInt() <= 0) {
                                        return@Button
                                    }

                                    onAceptar(
                                        propuesta,
                                        puntosAsignados
                                    )

                                    TempData.retosPropuestos.remove(propuesta)
                                    propuestas = TempData.retosPropuestos.toList()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF10B981)
                                )
                            ) {
                                Text("Aceptar")
                            }

                            Button(
                                onClick = {
                                    onRechazar(propuesta)
                                    TempData.retosPropuestos.remove(propuesta)
                                    propuestas = TempData.retosPropuestos.toList()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Rechazar")
                            }
                        }
                    }
                }
            }
        }
    }
}