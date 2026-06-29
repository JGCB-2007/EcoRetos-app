package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.UsuarioResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.ecoretosapp.data.model.InsigniaResponse
import com.example.ecoretosapp.data.model.ImpactoUsuarioResponse
class PerfilViewModel : ViewModel() {

    private val _insignias = MutableStateFlow<List<InsigniaResponse>>(emptyList())
    val insignias: StateFlow<List<InsigniaResponse>> = _insignias
    private val _usuario = MutableStateFlow<UsuarioResponse?>(null)
    val usuario: StateFlow<UsuarioResponse?> = _usuario

    private val _impacto = MutableStateFlow<ImpactoUsuarioResponse?>(null)
    val impacto: StateFlow<ImpactoUsuarioResponse?> = _impacto

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _todasLasInsignias = MutableStateFlow<List<InsigniaResponse>>(emptyList())
    val todasLasInsignias: StateFlow<List<InsigniaResponse>> = _todasLasInsignias

    fun cargarUsuario(idUsuario: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getUsuario(idUsuario)

                if (response.isSuccessful) {
                    _usuario.value = response.body()
                    _error.value = null
                } else {
                    _error.value = "No se pudo cargar el perfil"
                }
            } catch (e: Exception) {
                _error.value = "Error conexión: ${e.message}"
            }
        }
    }

    fun cargarTodasLasInsignias() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getInsignias()

                if (response.isSuccessful) {
                    _todasLasInsignias.value = response.body() ?: emptyList()
                }

            } catch (_: Exception) {

            }
        }
    }
    fun cargarImpacto(idUsuario: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getImpactoUsuario(idUsuario)

                if (response.isSuccessful) {
                    _impacto.value = response.body()
                    _error.value = null
                } else {
                    _error.value = "No se pudo cargar el impacto del usuario"
                }
            } catch (e: Exception) {
                _error.value = "Error conexión impacto: ${e.message}"
            }
        }
    }
    fun cargarInsignias(idUsuario: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getInsigniasUsuario(idUsuario)

                if (response.isSuccessful) {
                    _insignias.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "No se pudieron cargar las insignias"
                }
            } catch (e: Exception) {
                _error.value = "Error conexión insignias: ${e.message}"
            }
        }
    }
}
