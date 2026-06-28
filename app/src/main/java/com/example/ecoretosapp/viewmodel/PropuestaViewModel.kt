package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.CrearPropuestaRequest
import com.example.ecoretosapp.data.model.PropuestaResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PropuestaViewModel : ViewModel() {

    private val _misPropuestas = MutableStateFlow<List<PropuestaResponse>>(emptyList())
    val misPropuestas: StateFlow<List<PropuestaResponse>> = _misPropuestas

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarMisPropuestas(idUsuario: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getMisPropuestas(idUsuario)

                if (response.isSuccessful) {
                    _misPropuestas.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudieron cargar tus propuestas"
                }
            } catch (e: Exception) {
                _error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun crearPropuesta(
        idUsuario: Int,
        titulo: String,
        descripcion: String,
        categoria: String
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.crearPropuesta(
                    CrearPropuestaRequest(
                        idUsuario = idUsuario,
                        titulo = titulo,
                        descripcion = descripcion,
                        categoria = categoria
                    )
                )

                if (response.isSuccessful) {
                    _mensaje.value = "Propuesta enviada para revisión"
                    _error.value = null
                    cargarMisPropuestas(idUsuario)
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudo enviar la propuesta"
                }
            } catch (e: Exception) {
                _error.value = "Error al enviar propuesta: ${e.message}"
            }
        }
    }
}