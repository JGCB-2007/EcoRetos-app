package com.example.ecoretosapp.ui.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoretosapp.data.model.EvidenciaAdminResponse
import com.example.ecoretosapp.viewmodel.AdminEvidenciaViewModel
import coil.compose.AsyncImage
import com.example.ecoretosapp.ui.components.ConfirmacionDialog
import androidx.compose.foundation.clickable
import androidx.compose.ui.window.Dialog

@Composable
fun RevisarEvidenciasAdminScreen(
    volver: () -> Unit,
    onAprobar: (EvidenciaAdminResponse) -> Unit,
    onRechazar: (EvidenciaAdminResponse) -> Unit,
    viewModel: AdminEvidenciaViewModel = viewModel()
) {
    val evidencias by viewModel.evidencias.collectAsState()
    val error by viewModel.error.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    var evidenciaAprobar by remember { mutableStateOf<EvidenciaAdminResponse?>(null) }
    var evidenciaRechazar by remember { mutableStateOf<EvidenciaAdminResponse?>(null) }


    LaunchedEffect(Unit) {
        viewModel.cargarEvidenciasPendientes()
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

        if (error != null) {
            item {
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (mensaje != null) {
            item {
                Text(
                    text = mensaje ?: "",
                    color = Color(0xFF047857),
                    fontWeight = FontWeight.Bold
                )
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
                EvidenciaAdminCard(
                    evidencia = evidencia,
                    onAprobar = {
                        evidenciaAprobar = it
                    },
                    onRechazar = {
                        evidenciaRechazar = it
                    }
                )
            }
        }
    }
    if (evidenciaAprobar != null) {
        ConfirmacionDialog(
            titulo = "Aprobar evidencia",
            mensaje = "¿Seguro que deseas aprobar la evidencia de \"${evidenciaAprobar!!.tituloReto}\"?",
            textoConfirmar = "Aprobar",
            onConfirmar = {
                viewModel.aprobarEvidencia(evidenciaAprobar!!.idEvidencia)
                evidenciaAprobar = null
            },
            onCancelar = {
                evidenciaAprobar = null
            }
        )
    }

    if (evidenciaRechazar != null) {
        ConfirmacionDialog(
            titulo = "Rechazar evidencia",
            mensaje = "¿Seguro que deseas rechazar la evidencia de \"${evidenciaRechazar!!.tituloReto}\"?",
            textoConfirmar = "Rechazar",
            colorConfirmar = Color(0xFFEF4444),
            onConfirmar = {
                viewModel.rechazarEvidencia(evidenciaRechazar!!.idEvidencia)
                evidenciaRechazar = null
            },
            onCancelar = {
                evidenciaRechazar = null
            }
        )
    }
}

@Composable
fun EvidenciaAdminCard(
    evidencia: EvidenciaAdminResponse,
    onAprobar: (EvidenciaAdminResponse) -> Unit,
    onRechazar: (EvidenciaAdminResponse) -> Unit
) {
    var imagenAmpliada by remember { mutableStateOf(false) }
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
                text = evidencia.tituloReto,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Enviado por: ${evidencia.nombreUsuario}",
                fontSize = 14.sp
            )

            AssistChip(
                onClick = {},
                label = {
                    Text(
                        text = evidencia.estadoValidacion,
                        fontWeight = FontWeight.Bold
                    )
                }
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (evidencia.urlImagen.isNotBlank()) {
                        AsyncImage(
                            model = evidencia.urlImagen.trim(),
                            contentDescription = "Evidencia enviada",
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    imagenAmpliada = true
                                },
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
                    onClick = { onAprobar(evidencia) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF10B981)
                    )
                ) {
                    Text("Aprobar")
                }

                Button(
                    onClick = { onRechazar(evidencia) },
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
    if (imagenAmpliada) {
        Dialog(
            onDismissRequest = {
                imagenAmpliada = false
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(520.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = evidencia.tituloReto,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    AsyncImage(
                        model = evidencia.urlImagen.trim(),
                        contentDescription = "Evidencia ampliada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentScale = ContentScale.Fit
                    )

                    OutlinedButton(
                        onClick = {
                            imagenAmpliada = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}