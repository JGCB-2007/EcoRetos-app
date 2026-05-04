package com.example.ecoretosapp.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AdminInicioDesign(
    irCrearReto: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // HEADER
        item {
            Card(
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = "UAM EcoRetos",
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
                            .size(80.dp)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🧑‍💼", fontSize = 30.sp)
                    }
                }
            }
        }

        // TARJETAS SUPERIORES
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminResumenCard("🎯", "Retos activos", "12", Modifier.weight(1f))
                AdminResumenCard("📷", "Pendientes", "8", Modifier.weight(1f))
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                AdminResumenCard("🏆", "Insignias", "6", Modifier.weight(1f))
                AdminResumenCard("👥", "Usuarios", "120", Modifier.weight(1f))
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Acciones principales",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = irCrearReto,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("➕ Crear nuevo reto")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("📷 Revisar evidencias")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🏆 Crear insignia")
                    }
                }
            }
        }
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
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(icono, fontSize = 24.sp)

            Text(
                text = titulo,
                fontSize = 14.sp
            )

            Text(
                text = valor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}