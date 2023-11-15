package com.example.pokemonapp.data.models

import com.google.gson.annotations.SerializedName

class GlobalModel(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    var url: String
)