package com.romka_po.binchecker.interfaces

import com.romka_po.binchecker.model.Card
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    @GET("/{user}")
    suspend fun getInfoCard(@Path("user") user: String): Response<Card>
}