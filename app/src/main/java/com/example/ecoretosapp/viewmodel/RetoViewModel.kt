package com.example.ecoretosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoretosapp.data.api.RetrofitClient
import com.example.ecoretosapp.data.model.AceptarRetoRequest
import com.example.ecoretosapp.data.model.MisParticipacionesResponse
import com.example.ecoretosapp.data.model.Reto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class RetoViewModel : ViewModel() {

    private val _retos = MutableStateFlow<List<Reto>>(emptyList())
    val retos: StateFlow<List<Reto>> = _retos

    private val _retosAceptados = MutableStateFlow<Set<Int>>(emptySet())
    val retosAceptados: StateFlow<Set<Int>> = _retosAceptados

    private val _participaciones =
        MutableStateFlow<List<MisParticipacionesResponse>>(emptyList())

    val participaciones: StateFlow<List<MisParticipacionesResponse>> =
        _participaciones

    private val _puntosCompletados = MutableStateFlow(0)
    val puntosCompletados: StateFlow<Int> = _puntosCompletados

    private val _retosCompletados = MutableStateFlow<Set<Int>>(emptySet())
    val retosCompletados: StateFlow<Set<Int>> = _retosCompletados

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    /*
     * Estado utilizado para mostrar una pantalla de carga real.
     */
    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    /*
     * Contador para evitar que la carga desaparezca antes de tiempo
     * cuando hay más de una petición ejecutándose.
     */
    private var cargasActivas = 0

    private fun iniciarCarga() {
        cargasActivas++
        _cargando.value = true
    }

    private fun finalizarCarga() {
        cargasActivas = (cargasActivas - 1).coerceAtLeast(0)
        _cargando.value = cargasActivas > 0
    }

    fun cargarRetos() {
        iniciarCarga()

        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiService.getRetos()

                if (response.isSuccessful) {
                    _retos.value =
                        response.body() ?: emptyList()

                    _error.value = null
                } else {
                    _error.value =
                        "Error HTTP: ${response.code()}"
                }

            } catch (e: Exception) {
                _error.value =
                    "Error conexión: ${e.message}"
            } finally {
                finalizarCarga()
            }
        }
    }

    fun cargarParticipaciones(
        idUsuario: Int
    ) {
        iniciarCarga()

        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiService
                        .getParticipacionesUsuario(idUsuario)

                if (response.isSuccessful) {
                    val listaParticipaciones =
                        response.body() ?: emptyList()

                    _participaciones.value =
                        listaParticipaciones

                    _retosAceptados.value =
                        listaParticipaciones
                            .filter {
                                it.estado == "ACEPTADO" ||
                                        it.estado == "ENVIADO" ||
                                        it.estado == "EN_REVISION" ||
                                        it.estado == "APROBADO"
                            }
                            .map {
                                it.idReto
                            }
                            .toSet()

                    _error.value = null
                } else {
                    _error.value =
                        "Error cargando participaciones: " +
                                response.code()
                }

            } catch (e: Exception) {
                _error.value =
                    "Error conexión participaciones: " +
                            e.message
            } finally {
                finalizarCarga()
            }
        }
    }

    fun aceptarReto(
        idReto: Int,
        idUsuario: Int
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiService.aceptarReto(
                        idReto = idReto,
                        request = AceptarRetoRequest(
                            idUsuario = idUsuario
                        )
                    )

                if (response.isSuccessful) {
                    _retosAceptados.value =
                        _retosAceptados.value + idReto

                    _mensaje.value =
                        response.body()?.mensaje
                            ?: "Reto aceptado correctamente"

                    _error.value = null

                    cargarParticipaciones(idUsuario)
                } else {
                    val errorMsg =
                        response.errorBody()?.string()
                            ?: "No se pudo aceptar el reto"

                    if (
                        errorMsg.contains(
                            "aceptado",
                            ignoreCase = true
                        ) ||
                        errorMsg.contains(
                            "ya",
                            ignoreCase = true
                        )
                    ) {
                        _retosAceptados.value =
                            _retosAceptados.value + idReto

                        _mensaje.value =
                            "Este reto ya estaba aceptado"

                        _error.value = null
                    } else {
                        _error.value = errorMsg
                    }
                }

            } catch (e: Exception) {
                _error.value =
                    "Error conexión: ${e.message}"
            }
        }
    }

    fun cancelarRetoApi(
        idReto: Int,
        idUsuario: Int
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiService.cancelarReto(
                        idReto = idReto,
                        idUsuario = idUsuario
                    )

                if (response.isSuccessful) {
                    _mensaje.value =
                        "Reto cancelado correctamente"

                    _retosAceptados.value =
                        _retosAceptados.value - idReto

                    cargarRetos()
                    cargarParticipaciones(idUsuario)
                } else {
                    _error.value =
                        "No se puede cancelar el reto, un administrador esta verificando tu evidencia"
                }

            } catch (e: Exception) {
                _error.value =
                    "Error al cancelar: ${e.message}"
            }
        }
    }

    fun cancelarRetoLocal(
        idReto: Int
    ) {
        _retosAceptados.value =
            _retosAceptados.value - idReto

        _mensaje.value = "Reto cancelado"
        _error.value = null
    }

    fun enviarEvidenciaLocal() {
        _mensaje.value =
            "Evidencia enviada correctamente"

        _error.value = null
    }

    fun completarRetoLocal(
        idReto: Int,
        puntos: Int
    ) {
        if (!_retosCompletados.value.contains(idReto)) {
            _retosCompletados.value =
                _retosCompletados.value + idReto

            _puntosCompletados.value += puntos

            _mensaje.value =
                "Reto completado: +$puntos puntos"

            _error.value = null
        }
    }

    fun mostrarError(
        mensaje: String
    ) {
        _error.value = mensaje
    }

    fun enviarEvidenciaApi(
        idParticipacion: Int,
        imagen: MultipartBody.Part,
        idUsuario: Int
    ) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.apiService.enviarEvidencia(
                        idParticipacion = idParticipacion,
                        imagen = imagen
                    )

                if (response.isSuccessful) {
                    _mensaje.value =
                        response.body()?.mensaje
                            ?: "Evidencia enviada correctamente"

                    _error.value = null

                    cargarParticipaciones(idUsuario)
                } else {
                    val errorJson =
                        response.errorBody()?.string()

                    _error.value =
                        extraerMensajeError(
                            errorJson = errorJson,
                            mensajePorDefecto =
                                "No se pudo enviar la evidencia"
                        )
                }

            } catch (e: Exception) {
                _error.value =
                    "Error enviando evidencia: ${e.message}"
            }
        }
    }
}

fun extraerMensajeError(
    errorJson: String?,
    mensajePorDefecto: String
): String {
    if (errorJson.isNullOrBlank()) {
        return mensajePorDefecto
    }

    return try {
        val inicio =
            errorJson.indexOf("\"mensaje\":\"")

        if (inicio == -1) {
            return mensajePorDefecto
        }

        val desde =
            inicio + "\"mensaje\":\"".length

        val hasta =
            errorJson.indexOf("\"", desde)

        if (hasta == -1) {
            mensajePorDefecto
        } else {
            errorJson.substring(desde, hasta)
        }

    } catch (e: Exception) {
        mensajePorDefecto
    }
}