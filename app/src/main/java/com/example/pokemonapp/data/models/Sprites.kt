package com.example.pokemonapp.data.models

import com.google.gson.annotations.SerializedName

class Sprites(
    @SerializedName("back_default")
    var backDefault: String,
    @SerializedName("front_default")
    var frontDefault: String,
    @SerializedName("back_shiny")
    var backShiny: String,
    @SerializedName("front_shiny")
    var frontShiny: String,
)