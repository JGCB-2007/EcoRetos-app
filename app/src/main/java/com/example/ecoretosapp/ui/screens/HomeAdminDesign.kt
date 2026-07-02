package com.example.ecoretosapp.ui.screens

/**
 * Contiene el diseño visual de la pantalla principal del administrador.
 * Define la estructura y apariencia de los componentes mostrados
 * en la interfaz administrativa.
 */

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoretosapp.R
import com.example.ecoretosapp.ui.components.ConfirmacionDialog
import com.example.ecoretosapp.viewmodel.AdminDashboardViewModel
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

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

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                AdminResumenCard(
                    R.drawable.ic_retos_activos,
                    "Retos activos",
                    "${dashboard?.retosActivos ?: 0}",
                    Modifier.weight(1f)
                )

                AdminResumenCard(
                    R.drawable.ic_evidencias_dashboard,
                    "Evidencias",
                    "${dashboard?.evidenciasPendientes ?: 0}",
                    Modifier.weight(1f)
                )
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminResumenCard(
                    R.drawable.ic_insignias_dashboard,
                    "Insignias",
                    "${dashboard?.insigniasActivas ?: 0}",
                    Modifier.weight(1f)
                )

                AdminResumenCard(
                    R.drawable.ic_usuarios_dashboard,
                    "Usuarios",
                    "${dashboard?.usuariosRegistrados ?: 0}",
                    Modifier.weight(1f)
                )
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminResumenCard(
                    R.drawable.ic_propuestas_dashboard,
                    "Propuestas",
                    "${dashboard?.propuestasPendientes ?: 0}",
                    Modifier.weight(1f)
                )

                AdminResumenCard(
                    R.drawable.ic_sistema_dashboard,
                    "Sistema",
                    "Activo",
                    Modifier.weight(1f)
                )
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Text(
                        text = "Acciones principales",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    AdminActionPrimary(
                        icono = R.drawable.ic_crear_reto,
                        texto = "Crear nuevo reto",
                        onClick = irCrearReto
                    )

                    AdminActionSecondary(
                        icono = R.drawable.ic_revisar_evidencias,
                        texto = "Revisar evidencias",
                        onClick = irRevisarEvidencias
                    )

                    AdminActionSecondary(
                        icono = R.drawable.ic_crear_insignia,
                        texto = "Crear insignia",
                        onClick = irCrearInsignia
                    )

                    AdminActionSecondary(
                        icono = R.drawable.ic_propuestas,
                        texto = "Propuestas de estudiantes",
                        onClick = irPropuestas
                    )
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
    @DrawableRes icono: Int,
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
                    .background(
                        Color(0xFFD1FAE5),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icono),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
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
fun AdminActionPrimary(
    @DrawableRes icono: Int,
    texto: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF10B981),
                        Color(0xFF84CC16)
                    )
                ),
                RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        Color.White.copy(alpha = 0.95f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icono),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = texto,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "›",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun AdminActionSecondary(
    @DrawableRes icono: Int,
    texto: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .background(
                Color(0xFFF8FAFC),
                RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFBBF7D0),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp).background(
                        Color(0xFFD1FAE5),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icono),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = texto,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "›",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF64748B)
            )
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
                .height(56.dp)
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(50),
                    clip = false
                )
                .clip(RoundedCornerShape(50))
                .clickable { onClick() }
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(50)
                )
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = "Cerrar sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDC2626)
                )
            }
        }
    }
}