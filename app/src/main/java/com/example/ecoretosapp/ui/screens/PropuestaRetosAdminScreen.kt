package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoretosapp.data.model.PropuestaResponse
import com.example.ecoretosapp.viewmodel.AdminPropuestaViewModel

@Composable
fun PropuestasRetosAdminScreen(
    volver: () -> Unit,
    viewModel: AdminPropuestaViewModel = viewModel()
) {
    val propuestas by viewModel.propuestas.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarPropuestas()
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
        verticalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
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

        if (mensaje != null) {
            item {
                Text(
                    text = mensaje ?: "",
                    color = Color(0xFF047857),
                    fontWeight = FontWeight.Bold
                )
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
                PropuestaAdminCard(
                    propuesta = propuesta,
                    onAprobar = { puntos, dificultad, tipoValidacion, duracion, observacion ->
                        viewModel.aprobarPropuesta(
                            idPropuesta = propuesta.idPropuesta,
                            puntos = puntos,
                            dificultad = dificultad,
                            tipoValidacion = tipoValidacion,
                            duracionHoras = duracion,
                            observacionAdmin = observacion
                        )
                    },
                    onRechazar = { observacion ->
                        viewModel.rechazarPropuesta(
                            idPropuesta = propuesta.idPropuesta,
                            observacionAdmin = observacion
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PropuestaAdminCard(
    propuesta: PropuestaResponse,
    onAprobar: (
        puntos: Int,
        dificultad: String,
        tipoValidacion: String,
        duracionHoras: Int,
        observacionAdmin: String
    ) -> Unit,
    onRechazar: (observacionAdmin: String) -> Unit
) {
    var puntosAsignados by remember(propuesta.idPropuesta) { mutableStateOf("") }
    var dificultad by remember(propuesta.idPropuesta) { mutableStateOf("FACIL") }
    var tipoValidacion by remember(propuesta.idPropuesta) { mutableStateOf("MANUAL") }
    var duracionHoras by remember(propuesta.idPropuesta) { mutableStateOf("24") }
    var observacion by remember(propuesta.idPropuesta) { mutableStateOf("") }
    var mensajeLocal by remember(propuesta.idPropuesta) { mutableStateOf("") }

    var expandedDificultad by remember { mutableStateOf(false) }
    var expandedTipo by remember { mutableStateOf(false) }

    val dificultades = listOf("FACIL", "MEDIA", "ALTA")
    val tipos = listOf("MANUAL", "IA_APOYO", "HIBRIDA")

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = propuesta.titulo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Categoría: ${propuesta.categoria}",
                fontSize = 14.sp
            )

            Text(
                text = "Propuesto por: ${propuesta.nombreUsuario}",
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
                fontSize = 14.sp,
                color = Color(0xFF475569)
            )

            OutlinedTextField(
                value = puntosAsignados,
                onValueChange = { puntosAsignados = it },
                label = { Text("Puntos") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = campoPropuestaColores()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expandedDificultad,
                    onExpandedChange = { expandedDificultad = !expandedDificultad },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = dificultad,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Dificultad") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(16.dp),
                        colors = campoPropuestaColores()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedDificultad,
                        onDismissRequest = { expandedDificultad = false }
                    ) {
                        dificultades.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    dificultad = opcion
                                    expandedDificultad = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = expandedTipo,
                    onExpandedChange = { expandedTipo = !expandedTipo },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = tipoValidacion,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Validación") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(16.dp),
                        colors = campoPropuestaColores()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedTipo,
                        onDismissRequest = { expandedTipo = false }
                    ) {
                        tipos.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    tipoValidacion = opcion
                                    expandedTipo = false
                                }
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = duracionHoras,
                onValueChange = { duracionHoras = it },
                label = { Text("Duración en horas") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = campoPropuestaColores()
            )

            OutlinedTextField(
                value = observacion,
                onValueChange = { observacion = it },
                label = { Text("Observación del administrador") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(16.dp),
                colors = campoPropuestaColores()
            )

            if (mensajeLocal.isNotBlank()) {
                Text(
                    text = mensajeLocal,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        val puntos = puntosAsignados.toIntOrNull()
                        val duracion = duracionHoras.toIntOrNull()

                        when {
                            puntos == null -> {
                                mensajeLocal = "Los puntos deben ser un número válido"
                            }

                            puntos <= 0 -> {
                                mensajeLocal = "Los puntos deben ser mayores que 0"
                            }

                            duracion == null -> {
                                mensajeLocal = "La duración debe ser un número válido"
                            }

                            duracion <= 0 -> {
                                mensajeLocal = "La duración debe ser mayor que 0"
                            }

                            else -> {
                                mensajeLocal = ""
                                onAprobar(
                                    puntos,
                                    dificultad,
                                    tipoValidacion,
                                    duracion,
                                    observacion
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF10B981)
                    )
                ) {
                    Text("Aprobar")
                }

                Button(
                    onClick = {
                        onRechazar(observacion)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
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

@Composable
private fun campoPropuestaColores() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    focusedIndicatorColor = Color(0xFF10B981),
    focusedLabelColor = Color(0xFF10B981),
    cursorColor = Color(0xFF10B981),
    unfocusedIndicatorColor = Color(0xFF94A3B8)
)