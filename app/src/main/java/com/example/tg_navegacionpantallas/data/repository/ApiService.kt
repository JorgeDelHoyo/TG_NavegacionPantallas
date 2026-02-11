package com.example.tg_navegacionpantallas.data.repository

import com.example.tg_navegacionpantallas.data.model.Vivienda
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("vivienda")
    suspend fun getViviendas(): List<Vivienda>

    @POST("vivienda")
    suspend fun addVivienda(
        @Body vivienda: Vivienda
    ): Response<Vivienda>

    @PUT("vivienda/{id}")
    suspend fun updateVivienda(
        @Path("id") id: Int,
        @Body vivienda: Vivienda
    ): Response<Vivienda>

    @DELETE("vivienda/{id}")
    suspend fun deleteVivienda(
        @Path("id") id: Int
    ): Response<Unit>
}
