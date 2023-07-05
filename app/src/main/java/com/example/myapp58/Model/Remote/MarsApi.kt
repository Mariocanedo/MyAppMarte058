package com.example.myapp58.Model.Remote

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response

// incluye los verbos de solicitud

interface MarsApi {

    // vieja confiable
    @GET("realestate")
    fun fetchMarsData(): Call<List<MarsRealState>>



    // parte 2 coroutines
    @GET("realestate")
    suspend fun fetchMarsDataCoroutines(): Response<List<MarsRealState>>

}