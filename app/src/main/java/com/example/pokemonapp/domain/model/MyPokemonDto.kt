package com.example.pokemonapp.domain.model

import com.example.pokemonapp.data.models.PokemonModel
import com.google.gson.annotations.SerializedName

data class MyPokemonDto<T> (
    @SerializedName("success")
    var success: Boolean?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var data: T?
)