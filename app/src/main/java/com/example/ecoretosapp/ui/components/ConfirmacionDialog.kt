package com.example.ecoretosapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmacionDialog(
    titulo: String,
    mensaje: String,
    textoConfirmar: String = "Confirmar",
    textoCancelar: String = "Cancelar",
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelar,

        containerColor = Color.White,

        shape = RoundedCornerShape(24.dp),

        title = {
            Text(
                text = titulo,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        },

        text = {
            Text(
                text = mensaje,
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )
        },

        confirmButton = {
            Button(
                onClick = onConfirmar,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEF4444),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = textoConfirmar,
                    fontWeight = FontWeight.Bold
                )
            }
        },

        dismissButton = {
            OutlinedButton(
                onClick = onCancelar,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color(0xFF10B981)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF047857)
                )
            ) {
                Text(
                    text = textoCancelar,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}