package com.example.ecoretosapp.viewmodel

/**
 * ViewModel encargado de gestionar la lógica relacionada con los retos ecológicos.
 * Obtiene la información de los retos desde la API y administra el estado
 * que será mostrado en las pantallas correspondientes.
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.AceptarRetoRequest
import com.example.ecoretosapp.data.model.Reto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RetoViewModel : ViewModel() {

    private val _retos = MutableStateFlow<List<Reto>>(emptyList())
    val retos: StateFlow<List<Reto>> = _retos

    private val _retosAceptados = MutableStateFlow<Set<Int>>(emptySet())
    val retosAceptados: StateFlow<Set<Int>> = _retosAceptados

    private val _puntosCompletados = MutableStateFlow(0)
    val puntosCompletados: StateFlow<Int> = _puntosCompletados

    private val _retosCompletados = MutableStateFlow<Set<Int>>(emptySet())
    val retosCompletados: StateFlow<Set<Int>> = _retosCompletados

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun cargarRetos() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getRetos()

                if (response.isSuccessful) {
                    _retos.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Error HTTP: ${response.code()}"
                }

            } catch (e: Exception) {
                _error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun aceptarReto(idReto: Int, idUsuario: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.aceptarReto(
                    idReto = idReto,
                    request = AceptarRetoRequest(idUsuario = idUsuario)
                )

                if (response.isSuccessful) {
                    _retosAceptados.value = _retosAceptados.value + idReto
                    _mensaje.value = response.body()?.mensaje ?: "Reto aceptado correctamente"
                    _error.value = null
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "No se pudo aceptar el reto"

                    if (
                        errorMsg.contains("aceptado", ignoreCase = true) ||
                        errorMsg.contains("ya", ignoreCase = true)
                    ) {
                        _retosAceptados.value = _retosAceptados.value + idReto
                        _mensaje.value = "Este reto ya estaba aceptado"
                        _error.value = null
                    } else {
                        _error.value = errorMsg
                    }
                }

            } catch (e: Exception) {
                _error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun cancelarRetoApi(idReto: Int, idUsuario: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.cancelarReto(
                    idReto = idReto,
                    idUsuario = idUsuario
                )

                if (response.isSuccessful) {
                    _mensaje.value = "Reto cancelado correctamente"

                    _retosAceptados.value = _retosAceptados.value - idReto

                    cargarRetos()
                    cargarParticipaciones(idUsuario)
                } else {
                    _error.value = "No se pudo cancelar el reto"
                }
            } catch (e: Exception) {
                _error.value = "Error al cancelar: ${e.message}"
            }
        }
    }

    fun cancelarRetoLocal(idReto: Int) {
        _retosAceptados.value = _retosAceptados.value - idReto
        _mensaje.value = "Reto cancelado"
        _error.value = null
    }

    fun enviarEvidenciaLocal() {
        _mensaje.value = "Evidencia enviada correctamente"
        _error.value = null
    }

    fun completarRetoLocal(idReto: Int, puntos: Int) {
        if (!_retosCompletados.value.contains(idReto)) {
            _retosCompletados.value = _retosCompletados.value + idReto
            _puntosCompletados.value += puntos
            _mensaje.value = "Reto completado: +$puntos puntos"
            _error.value = null
        }
    }

    fun cargarParticipaciones(idUsuario: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getParticipacionesUsuario(idUsuario)

                if (response.isSuccessful) {
                    val participaciones = response.body() ?: emptyList()

                    _retosAceptados.value = participaciones
                        .filter {
                            it.estado == "ACEPTADO" ||
                                    it.estado == "ENVIADO" ||
                                    it.estado == "APROBADO"
                        }
                        .map { it.idReto }
                        .toSet()

                    _error.value = null
                } else {
                    _error.value = "Error cargando participaciones: ${response.code()}"
                }

            } catch (e: Exception) {
                _error.value = "Error conexión participaciones: ${e.message}"
            }
        }
    }
}