package com.example.ecoretosapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ecoretosapp.data.TempData
import com.example.ecoretosapp.data.model.Evidencia

@Composable
fun RevisarEvidenciasAdminScreen(
    volver: () -> Unit,
    onAprobar: (Evidencia) -> Unit,
    onRechazar: (Evidencia) -> Unit
) {
    var evidencias by remember {
        mutableStateOf(TempData.evidenciasPendientes.toList())
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
                        text = "Revisar evidencias",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Aprueba o rechaza las evidencias enviadas por estudiantes",
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

        if (evidencias.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Text(
                        text = "No hay evidencias pendientes",
                        modifier = Modifier.padding(20.dp),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            items(evidencias) { evidencia ->
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
                            text = evidencia.nombreReto,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Enviado por: ${evidencia.enviadoPor}",
                            fontSize = 14.sp
                        )

                        AssistChip(
                            onClick = {},
                            label = {
                                Text(
                                    text = evidencia.estado,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )

                        if (evidencia.comentario.isNotBlank()) {
                            Text(
                                text = evidencia.comentario,
                                fontSize = 14.sp
                            )
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (evidencia.imagenUri.isNotBlank()) {
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            Uri.parse(evidencia.imagenUri)
                                        ),
                                        contentDescription = "Evidencia enviada",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Text("Sin imagen")
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    onAprobar(evidencia)
                                    TempData.evidenciasPendientes.remove(evidencia)
                                    evidencias = TempData.evidenciasPendientes.toList()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF10B981)
                                )
                            ) {
                                Text("Aprobar")
                            }

                            Button(
                                onClick = {
                                    onRechazar(evidencia)
                                    TempData.evidenciasPendientes.remove(evidencia)
                                    evidencias = TempData.evidenciasPendientes.toList()
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