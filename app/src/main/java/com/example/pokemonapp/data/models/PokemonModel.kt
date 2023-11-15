package com.example.pokemonapp.data.models

import com.google.gson.annotations.SerializedName

data class PokemonModel (
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var pokemonName: String,
    @SerializedName("nickname")
    var nickname: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("height")
    var height: Int,
    @SerializedName("weight")
    var weight: Int,
    @SerializedName("base_experience")
    var baseExperience: Int,
    @SerializedName("abilities")
    var abilities: List<Ability>,
    @SerializedName("moves")
    var moves: List<Move>,
    @SerializedName("sprites")
    var sprites: Sprites,
    @SerializedName("species")
    var species: GlobalModel,
    @SerializedName("stats")
    var stats: List<Stats>,
    @SerializedName("types")
    var types: List<Type>
)