package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.ecoretosapp.data.model.Reto
import com.example.ecoretosapp.ui.components.ConfirmacionDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRetoDesign(
    nombre: String,
    categoria: String,
    puntos: String,
    descripcion: String,
    tipoValidacion: String,
    duracionHoras: String,
    mensajeError: String,
    retos: List<Reto>,
    onNombreChange: (String) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onPuntosChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onTipoValidacionChange: (String) -> Unit,
    onDuracionHorasChange: (String) -> Unit,
    onGuardar: () -> Unit,
    onEditar: (Reto) -> Unit,
    onEliminar: (Reto) -> Unit,
    volver: () -> Unit
) {
    var expandedDificultad by remember { mutableStateOf(false) }
    var expandedTipoValidacion by remember { mutableStateOf(false) }
    var retoAEliminar by remember { mutableStateOf<Reto?>(null) }
    val dificultades = listOf("FACIL", "MEDIA", "ALTA")
    val tiposValidacion = listOf("MANUAL", "IA_APOYO", "HIBRIDA")

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
        verticalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            HeaderCrearReto()
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Nuevo reto",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = onNombreChange,
                        label = { Text("Nombre del reto") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp),
                        colors = campoColores()
                    )

                    ExposedDropdownMenuBox(
                        expanded = expandedDificultad,
                        onExpandedChange = { expandedDificultad = !expandedDificultad }
                    ) {
                        OutlinedTextField(
                            value = categoria,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Dificultad") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            singleLine = true,
                            shape = RoundedCornerShape(18.dp),
                            colors = campoColores()
                        )

                        ExposedDropdownMenu(
                            expanded = expandedDificultad,
                            onDismissRequest = { expandedDificultad = false }
                        ) {
                            dificultades.forEach { dificultad ->
                                DropdownMenuItem(
                                    text = { Text(dificultad) },
                                    onClick = {
                                        onCategoriaChange(dificultad)
                                        expandedDificultad = false
                                    }
                                )
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = expandedTipoValidacion,
                        onExpandedChange = { expandedTipoValidacion = !expandedTipoValidacion }
                    ) {
                        OutlinedTextField(
                            value = tipoValidacion,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tipo de validación") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            singleLine = true,
                            shape = RoundedCornerShape(18.dp),
                            colors = campoColores()
                        )

                        ExposedDropdownMenu(
                            expanded = expandedTipoValidacion,
                            onDismissRequest = { expandedTipoValidacion = false }
                        ) {
                            tiposValidacion.forEach { tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo) },
                                    onClick = {
                                        onTipoValidacionChange(tipo)
                                        expandedTipoValidacion = false
                                    }
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = puntos,
                            onValueChange = onPuntosChange,
                            label = { Text("Puntos") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(18.dp),
                            colors = campoColores()
                        )

                        OutlinedTextField(
                            value = duracionHoras,
                            onValueChange = onDuracionHorasChange,
                            label = { Text("Horas") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(18.dp),
                            colors = campoColores()
                        )
                    }

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = onDescripcionChange,
                        label = { Text("Descripción") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = campoColores()
                    )

                    if (mensajeError.isNotBlank()) {
                        Text(
                            text = mensajeError,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = onGuardar,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF10B981)
                        )
                    ) {
                        Text(
                            text = "Guardar reto",
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
                        Text(
                            text = "Volver",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Retos registrados",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        if (retos.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Text(
                        text = "No hay retos registrados.",
                        modifier = Modifier.padding(18.dp),
                        fontSize = 15.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }
        } else {
            items(retos) { reto ->
                RetoAdminCard(
                    reto = reto,
                    onEditar = { onEditar(reto) },
                    onEliminar = { retoAEliminar = reto }
                )
            }
        }
    }
    if (retoAEliminar != null) {
        ConfirmacionDialog(
            titulo = "Desactivar reto",
            mensaje = "¿Seguro que deseas desactivar el reto \"${retoAEliminar!!.titulo}\"?",
            textoConfirmar = "Desactivar",
            textoCancelar = "Cancelar",
            onConfirmar = {
                onEliminar(retoAEliminar!!)
                retoAEliminar = null
            },
            onCancelar = {
                retoAEliminar = null
            }
        )
    }
}

@Composable
private fun HeaderCrearReto() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Text(
                text = "Administración",
                color = Color(0xFF047857),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Crear y gestionar retos",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Registra, edita o desactiva retos ecológicos desde la API.",
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )
        }
    }
}

@Composable
private fun RetoAdminCard(
    reto: Reto,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                Color(0xFFD1FAE5),
                                RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🎯",
                            fontSize = 26.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = reto.titulo,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "${reto.dificultad} • ${reto.tipoValidacion} • ${reto.puntos} puntos",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )

                        if (reto.duracionHoras != null) {
                            Text(
                                text = "Duración: ${reto.duracionHoras} horas",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }

                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = "Activo",
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }

            Text(
                text = reto.descripcion,
                fontSize = 14.sp,
                color = Color(0xFF475569)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onEditar,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEDE9FE)
                    )
                ) {
                    Text(
                        text = "✏️ Editar",
                        color = Color(0xFF4C1D95),
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onEliminar,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFEE2E2)
                    )
                ) {
                    Text(
                        text = "🗑️ Desactivar",
                        color = Color(0xFF991B1B),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun campoColores() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    disabledContainerColor = Color.White,
    focusedIndicatorColor = Color(0xFF10B981),
    focusedLabelColor = Color(0xFF10B981),
    cursorColor = Color(0xFF10B981),
    unfocusedIndicatorColor = Color(0xFF94A3B8)
)