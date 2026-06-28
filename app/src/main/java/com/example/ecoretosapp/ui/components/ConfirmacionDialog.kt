package com.example.ecoretosapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.ecoretosapp.ui.components.ConfirmacionDialog
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
        title = {
            Text(titulo)
        },
        text = {
            Text(mensaje)
        },
        confirmButton = {
            TextButton(onClick = onConfirmar) {
                Text(textoConfirmar)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text(textoCancelar)
            }
        }
    )
}