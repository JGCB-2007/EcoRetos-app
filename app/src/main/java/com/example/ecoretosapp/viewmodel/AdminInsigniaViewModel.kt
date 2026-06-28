package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.CrearInsigniaRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminInsigniaViewModel : ViewModel() {

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun crearInsignia(
        nombre: String,
        descripcion: String,
        puntosMinimos: Int,
        iconoUrl: String?
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.crearInsignia(
                    CrearInsigniaRequest(
                        nombre = nombre,
                        descripcion = descripcion,
                        puntosMinimos = puntosMinimos,
                        iconoUrl = iconoUrl
                    )
                )

                if (response.isSuccessful) {
                    _mensaje.value = "Insignia creada correctamente"
                    _error.value = null
                } else {
                    _error.value = response.errorBody()?.string()
                        ?: "No se pudo crear la insignia"
                }

            } catch (e: Exception) {
                _error.value = "Error al crear insignia: ${e.message}"
            }
        }
    }
}