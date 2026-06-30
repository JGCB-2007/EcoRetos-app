package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.ImpactoUsuarioResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ImpactoUsuarioViewModel : ViewModel() {

    private val _impacto =
        MutableStateFlow<ImpactoUsuarioResponse?>(null)

    val impacto: StateFlow<ImpactoUsuarioResponse?> =
        _impacto

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error

    /*
     * Indica si los datos del impacto
     * se están consultando en la API.
     */
    private val _cargando =
        MutableStateFlow(false)

    val cargando: StateFlow<Boolean> =
        _cargando

    fun cargarImpacto(
        idUsuario: Int
    ) {
        viewModelScope.launch {

            _cargando.value = true
            _error.value = null

            try {
                val response =
                    RetrofitClient.apiService
                        .getImpactoUsuario(idUsuario)

                if (response.isSuccessful) {
                    _impacto.value =
                        response.body()

                    _error.value = null
                } else {
                    _error.value =
                        "No se pudo cargar el impacto"
                }

            } catch (e: Exception) {
                _error.value =
                    "Error impacto: ${e.message}"

            } finally {
                /*
                 * La carga termina cuando la API
                 * responde o produce un error.
                 */
                _cargando.value = false
            }
        }
    }
}