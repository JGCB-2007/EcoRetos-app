package com.example.ecoretosapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.ecoretosapp.viewmodel.RetoViewModel
import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import androidx.compose.ui.platform.LocalContext

@Composable
fun EnviarEvidenciaScreen(
    idParticipacion: Int,
    idUsuario: Int,
    nombreReto: String,
    viewModel: RetoViewModel,
    onEnviar: () -> Unit,
    volver: () -> Unit
){

    var comentario by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val seleccionarImagen = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagenUri = uri
    }

    Column(
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
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        Text(
            text = "Enviar evidencia",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Card(
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = nombreReto,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Adjunta una fotografía como evidencia del reto realizado."
                )
            }
        }

        OutlinedTextField(
            value = comentario,
            onValueChange = {
                comentario = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Comentario (opcional)")
            }
        )

        Button(
            onClick = {
                seleccionarImagen.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar fotografía")
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (imagenUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imagenUri),
                        contentDescription = "Evidencia seleccionada",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("Vista previa de imagen")
                }
            }
        }

        Button(
            onClick = {
                if (imagenUri == null) {
                    return@Button
                }

                val imagenMultipart = crearMultipartDesdeUri(
                    context = context,
                    uri = imagenUri!!,
                    nombreCampo = "imagen"
                )

                viewModel.enviarEvidenciaApi(
                    idParticipacion = idParticipacion,
                    imagen = imagenMultipart,
                    idUsuario = idUsuario
                )

                onEnviar()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text("Enviar evidencia")
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
fun crearMultipartDesdeUri(
    context: Context,
    uri: Uri,
    nombreCampo: String
): MultipartBody.Part {
    val inputStream = context.contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("No se pudo abrir la imagen")

    val archivoTemporal = File.createTempFile(
        "evidencia_",
        ".jpg",
        context.cacheDir
    )

    FileOutputStream(archivoTemporal).use { outputStream ->
        inputStream.copyTo(outputStream)
    }

    val requestBody = archivoTemporal
        .asRequestBody("image/jpeg".toMediaTypeOrNull())

    return MultipartBody.Part.createFormData(
        nombreCampo,
        archivoTemporal.name,
        requestBody
    )
}