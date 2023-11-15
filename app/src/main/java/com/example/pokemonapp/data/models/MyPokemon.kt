package com.example.pokemonapp.data.models

import com.google.gson.annotations.SerializedName

class MyPokemon(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("name")
    var name: String,
    @SerializedName("nickname")
    var nickname: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("index")
    var index: Int
)