package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.PropuestaResponse
import com.example.ecoretosapp.data.model.RevisionPropuestaRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminPropuestaViewModel : ViewModel() {

    private val _propuestas = MutableStateFlow<List<PropuestaResponse>>(emptyList())
    val propuestas: StateFlow<List<PropuestaResponse>> = _propuestas

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarPropuestas() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getPropuestasPendientes()

                if (response.isSuccessful) {
                    _propuestas.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudieron cargar las propuestas"
                }
            } catch (e: Exception) {
                _error.value = "Error conexión propuestas: ${e.message}"
            }
        }
    }

    fun aprobarPropuesta(
        idPropuesta: Int,
        puntos: Int,
        dificultad: String,
        tipoValidacion: String,
        duracionHoras: Int,
        observacionAdmin: String = "",
        idAdministrador: Int = 1
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.aprobarPropuesta(
                    idPropuesta,
                    RevisionPropuestaRequest(
                        idAdministrador = idAdministrador,
                        puntos = puntos,
                        dificultad = dificultad,
                        tipoValidacion = tipoValidacion,
                        duracionHoras = duracionHoras,
                        observacionAdmin = observacionAdmin
                    )
                )

                if (response.isSuccessful) {
                    _mensaje.value = "Propuesta aprobada y convertida en reto"
                    _error.value = null
                    cargarPropuestas()
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudo aprobar la propuesta"
                }
            } catch (e: Exception) {
                _error.value = "Error al aprobar propuesta: ${e.message}"
            }
        }
    }

    fun rechazarPropuesta(
        idPropuesta: Int,
        observacionAdmin: String = "",
        idAdministrador: Int = 1
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.rechazarPropuesta(
                    idPropuesta,
                    RevisionPropuestaRequest(
                        idAdministrador = idAdministrador,
                        observacionAdmin = observacionAdmin
                    )
                )

                if (response.isSuccessful) {
                    _mensaje.value = "Propuesta rechazada"
                    _error.value = null
                    cargarPropuestas()
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudo rechazar la propuesta"
                }
            } catch (e: Exception) {
                _error.value = "Error al rechazar propuesta: ${e.message}"
            }
        }
    }
}