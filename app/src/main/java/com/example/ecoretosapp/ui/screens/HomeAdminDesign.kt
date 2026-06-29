package com.example.ecoretosapp.ui.screens


/**
 * Contiene el diseño visual de la pantalla principal del administrador.
 * Define la estructura y apariencia de los componentes mostrados
 * en la interfaz administrativa.
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoretosapp.viewmodel.AdminDashboardViewModel
import com.example.ecoretosapp.ui.components.ConfirmacionDialog
import androidx.compose.runtime.*

@Composable
fun AdminInicioDesign(
    irCrearReto: () -> Unit,
    irPropuestas: () -> Unit,
    irRevisarEvidencias: () -> Unit,
    irCrearInsignia: () -> Unit,
    onLogout: () -> Unit,
    dashboardViewModel: AdminDashboardViewModel = viewModel()
){

    val dashboard by dashboardViewModel.dashboard.collectAsState()
    val error by dashboardViewModel.error.collectAsState()
    var confirmarLogout by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        dashboardViewModel.cargarDashboard()
    }
    LazyColumn(
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

        // HEADER
        item {
            Card(
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFF10B981),
                                    Color(0xFF84CC16)
                                )
                            )
                        )
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = "EcoRetos UAM",
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = "Administrador",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = "Control de retos, evidencias e insignias.",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🧑‍💼", fontSize = 26.sp)
                    }
                }
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
        // TARJETAS SUPERIORES
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminResumenCard("🎯", "Retos activos", "${dashboard?.retosActivos ?: 0}", Modifier.weight(1f))
                AdminResumenCard("📷", "Evidencias", "${dashboard?.evidenciasPendientes ?: 0}", Modifier.weight(1f))
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminResumenCard("🏆", "Insignias", "${dashboard?.insigniasActivas ?: 0}", Modifier.weight(1f))
                AdminResumenCard("👥", "Usuarios", "${dashboard?.usuariosRegistrados ?: 0}", Modifier.weight(1f))
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminResumenCard("📝", "Propuestas", "${dashboard?.propuestasPendientes ?: 0}", Modifier.weight(1f))
                AdminResumenCard("📊", "Sistema", "Activo", Modifier.weight(1f))
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface // mismo blanco que los otros
                ),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Acciones principales",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { irCrearReto() }
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFF10B981),
                                        Color(0xFF84CC16)
                                    )
                                ),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "➕ Crear nuevo reto",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = irRevisarEvidencias,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "📷 Revisar evidencias",
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = irCrearInsignia,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "🏆 Crear insignia",
                            color = Color.Black
                        )
                    }
                    OutlinedButton(
                        onClick = irPropuestas,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = "📝 Propuestas de estudiantes",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    LogoutAdminCard(
                        onClick = {
                            confirmarLogout = true
                        }
                    )
                }
            }
        }

    }

    if (confirmarLogout) {
        ConfirmacionDialog(
            titulo = "Cerrar sesión",
            mensaje = "¿Seguro que deseas cerrar sesión?",
            textoConfirmar = "Cerrar sesión",
            textoCancelar = "Cancelar",
            onConfirmar = {
                confirmarLogout = false
                onLogout()
            },
            onCancelar = {
                confirmarLogout = false
            }
        )
    }
}

@Composable
fun AdminResumenCard(
    icono: String,
    titulo: String,
    valor: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier.height(92.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0xFFD1FAE5), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(icono, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = titulo,
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    maxLines = 1
                )

                Text(
                    text = valor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }
        }
    }
}

@Composable
fun LogoutAdminCard(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clickable { onClick() }
                .shadow(
                    elevation = 6.dp, // 🔥 sombra
                    shape = RoundedCornerShape(50),
                    clip = false
                )
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(50)
                )
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(50)
                )
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🚪", fontSize = 18.sp)

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Cerrar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }
        }
    }
}
