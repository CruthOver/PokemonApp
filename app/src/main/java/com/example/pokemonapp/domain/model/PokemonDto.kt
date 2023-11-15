package com.example.pokemonapp.domain.model

import com.example.pokemonapp.data.models.PokemonModel
import com.google.gson.annotations.SerializedName

data class PokemonDto (
    @SerializedName("count")
    var count: Int?,
    @SerializedName("next")
    var next: String?,
    @SerializedName("previous")
    var prev: String?,
    @SerializedName("results")
    var results: List<PokemonModel>
)