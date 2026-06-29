package com.example.ecoretosapp.data.api

/**
 * Define las operaciones disponibles para comunicarse con la API del sistema EcoRetos.
 * Aquí se especifican los endpoints (GET, POST, etc.) como login, obtención de retos
 * y registro de participación de usuarios.
 */

import com.example.ecoretosapp.data.model.LoginRequest
import com.example.ecoretosapp.data.model.LoginResponse
import com.example.ecoretosapp.data.model.Reto
import com.example.ecoretosapp.data.model.AceptarRetoRequest
import com.example.ecoretosapp.data.model.ParticipacionResponse
import com.example.ecoretosapp.data.model.EvidenciaAdminResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import com.example.ecoretosapp.data.model.MisParticipacionesResponse
import com.example.ecoretosapp.data.model.EvidenciaResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import com.example.ecoretosapp.data.model.AccionEvidenciaResponse
import retrofit2.http.PUT
import com.example.ecoretosapp.data.model.RankingResponse
import com.example.ecoretosapp.data.model.UsuarioResponse
import com.example.ecoretosapp.data.model.InsigniaResponse
import com.example.ecoretosapp.data.model.CrearRetoRequest
import com.example.ecoretosapp.data.model.EditarRetoRequest
import com.example.ecoretosapp.data.model.CrearPropuestaRequest
import com.example.ecoretosapp.data.model.PropuestaResponse
import com.example.ecoretosapp.data.model.RevisionPropuestaRequest
import com.example.ecoretosapp.data.model.ImpactoUsuarioResponse
import com.example.ecoretosapp.data.model.CrearInsigniaRequest


interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("retos")
    suspend fun getRetos(): Response<List<Reto>>

    @POST("retos/{idReto}/aceptar")
    suspend fun aceptarReto(
        @Path("idReto") idReto: Int,
        @Body request: AceptarRetoRequest
    ): Response<ParticipacionResponse>

    @DELETE("retos/{idReto}/cancelar/{idUsuario}")
    suspend fun cancelarReto(
        @Path("idReto") idReto: Int,
        @Path("idUsuario") idUsuario: Int
    ): Response<ResponseBody>

    @GET("usuarios/{idUsuario}/participaciones")
    suspend fun getParticipacionesUsuario(
        @Path("idUsuario") idUsuario: Int
    ): Response<List<MisParticipacionesResponse>>

    @Multipart
    @POST("participaciones/{idParticipacion}/evidencia")
    suspend fun enviarEvidencia(
        @Path("idParticipacion") idParticipacion: Int,
        @Part imagen: MultipartBody.Part
    ): Response<EvidenciaResponse>

    @GET("admin/evidencias/pendientes")
    suspend fun getEvidenciasPendientes(): Response<List<EvidenciaAdminResponse>>

    @PUT("admin/evidencias/{idEvidencia}/aprobar")
    suspend fun aprobarEvidencia(
        @Path("idEvidencia") idEvidencia: Int
    ): Response<AccionEvidenciaResponse>

    @PUT("admin/evidencias/{idEvidencia}/rechazar")
    suspend fun rechazarEvidencia(
        @Path("idEvidencia") idEvidencia: Int
    ): Response<AccionEvidenciaResponse>

    @GET("ranking")
    suspend fun getRanking(): Response<List<RankingResponse>>

    @GET("usuarios/{idUsuario}")
    suspend fun getUsuario(
        @Path("idUsuario") idUsuario: Int
    ): Response<UsuarioResponse>

    @GET("insignias/usuario/{idUsuario}")
    suspend fun getInsigniasUsuario(
        @Path("idUsuario") idUsuario: Int
    ): Response<List<InsigniaResponse>>

    @POST("admin/retos")
    suspend fun crearRetoAdmin(
        @Body request: CrearRetoRequest
    ): Response<Reto>

    @PUT("admin/retos/{idReto}")
    suspend fun editarRetoAdmin(
        @Path("idReto") idReto: Int,
        @Body request: EditarRetoRequest
    ): Response<Reto>

    @DELETE("admin/retos/{idReto}")
    suspend fun eliminarRetoAdmin(
        @Path("idReto") idReto: Int
    ): Response<ResponseBody>

    @GET("admin/retos")
    suspend fun getRetosAdmin(): Response<List<Reto>>

    @POST("propuestas")
    suspend fun crearPropuesta(
        @Body request: CrearPropuestaRequest
    ): Response<PropuestaResponse>

    @GET("propuestas/mias/{idUsuario}")
    suspend fun getMisPropuestas(
        @Path("idUsuario") idUsuario: Int
    ): Response<List<PropuestaResponse>>

    @GET("admin/propuestas")
    suspend fun getPropuestasPendientes(): Response<List<PropuestaResponse>>

    @PUT("admin/propuestas/{id}/aprobar")
    suspend fun aprobarPropuesta(
        @Path("id") idPropuesta: Int,
        @Body request: RevisionPropuestaRequest
    ): Response<PropuestaResponse>

    @PUT("admin/propuestas/{id}/rechazar")
    suspend fun rechazarPropuesta(
        @Path("id") idPropuesta: Int,
        @Body request: RevisionPropuestaRequest
    ): Response<PropuestaResponse>

    @GET("usuarios/{idUsuario}/impacto")
    suspend fun getImpactoUsuario(
        @Path("idUsuario") idUsuario: Int
    ): Response<ImpactoUsuarioResponse>

    @POST("admin/insignias")
    suspend fun crearInsignia(
        @Body request: CrearInsigniaRequest
    ): Response<InsigniaResponse>

    @GET("insignias")
    suspend fun getInsignias(): Response<List<InsigniaResponse>>
}