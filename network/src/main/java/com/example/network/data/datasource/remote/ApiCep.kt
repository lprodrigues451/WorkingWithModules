package com.example.network.data.datasource.remote

import com.example.network.data.model.UserResult
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCep {
    @GET("v1/cep/{cep}")
    suspend fun getAddress(@Path("cep") cep:String): UserResult
}