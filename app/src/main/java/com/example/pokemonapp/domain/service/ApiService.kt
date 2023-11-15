package com.example.pokemonapp.domain.service

import com.example.pokemonapp.data.models.MyPokemon
import com.example.pokemonapp.data.models.PokemonModel
import com.example.pokemonapp.domain.model.MyPokemonDto
import com.example.pokemonapp.domain.model.PokemonDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemon(): Response<PokemonDto>

    @GET("pokemon/{id}")
    suspend fun getDetailPokemon(@Path("id") id:Int): Response<PokemonModel>

    @POST("pokemon")
    suspend fun catchPokemon(@Body myPokemon: MyPokemon): Response<MyPokemonDto<MyPokemon>>

    @GET("pokemon")
    suspend fun getMyPokemons(): Response<MyPokemonDto<List<MyPokemon>>>

    @DELETE("pokemon/{id}")
    suspend fun releasePokemon(@Path("id") id:Int): Response<MyPokemonDto<ResponseBody>>
}