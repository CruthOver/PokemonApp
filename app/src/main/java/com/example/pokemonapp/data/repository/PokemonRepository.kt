package com.example.pokemonapp.data.repository

import com.example.pokemonapp.data.models.MyPokemon
import com.example.pokemonapp.data.models.PokemonModel
import com.example.pokemonapp.domain.model.MyPokemonDto
import com.example.pokemonapp.domain.model.PokemonDto
import com.example.pokemonapp.domain.service.RetrofitClient
import com.example.pokemonapp.domain.service.RetrofitClient2
import okhttp3.ResponseBody
import retrofit2.Response

class PokemonRepository {
    private val apiService = RetrofitClient.apiService
    private val apiService2 = RetrofitClient2.apiService

    suspend fun getPokemon(): Response<PokemonDto> {
        return apiService.getPokemon()
    }

    suspend fun getDetailPokemon(id: Int): Response<PokemonModel> {
        return apiService.getDetailPokemon(id)
    }

    suspend fun getMyPokemon(): Response<MyPokemonDto<List<MyPokemon>>> {
        return apiService2.getMyPokemons()
    }

    suspend fun catchPokemon(myPokemon: MyPokemon): Response<MyPokemonDto<MyPokemon>> {
        return apiService2.catchPokemon(myPokemon);
    }

    suspend fun releasePokemon(id: Int): Response<MyPokemonDto<ResponseBody>> {
        return apiService2.releasePokemon(id);
    }
}