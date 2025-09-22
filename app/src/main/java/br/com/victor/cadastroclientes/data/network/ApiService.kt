package br.com.victor.cadastroclientes.data.network

import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}