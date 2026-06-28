package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.EvidenciaAdminResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminEvidenciaViewModel : ViewModel() {

    private val _evidencias = MutableStateFlow<List<EvidenciaAdminResponse>>(emptyList())
    val evidencias: StateFlow<List<EvidenciaAdminResponse>> = _evidencias

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarEvidenciasPendientes() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getEvidenciasPendientes()

                if (response.isSuccessful) {
                    _evidencias.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "No se pudieron cargar las evidencias"
                }

            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            }
        }
    }
    fun aprobarEvidencia(idEvidencia: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.aprobarEvidencia(idEvidencia)

                if (response.isSuccessful) {
                    _mensaje.value = response.body()?.mensaje ?: "Evidencia aprobada"
                    _error.value = null
                    cargarEvidenciasPendientes()
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudo aprobar"
                }

            } catch (e: Exception) {
                _error.value = "Error al aprobar: ${e.message}"
            }
        }
    }

    fun rechazarEvidencia(idEvidencia: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.rechazarEvidencia(idEvidencia)

                if (response.isSuccessful) {
                    _mensaje.value = response.body()?.mensaje ?: "Evidencia rechazada"
                    _error.value = null
                    cargarEvidenciasPendientes()
                } else {
                    _error.value = response.errorBody()?.string() ?: "No se pudo rechazar"
                }

            } catch (e: Exception) {
                _error.value = "Error al rechazar: ${e.message}"
            }
        }
    }
}