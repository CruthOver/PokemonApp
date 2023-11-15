package com.example.pokemonapp.domain.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val baseURL = "https://pokeapi.co/api/v2/"

    val apiService: ApiService = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)
}

object RetrofitClient2 {
    private const val baseURL = "http://192.168.142.94:81/api/"

    val apiService: ApiService = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)
}