package com.example.pokemonapp.data.models

import com.google.gson.annotations.SerializedName

class Stats(
    @SerializedName("base_stat")
    var baseStat: Int,
    @SerializedName("effort")
    var effort: Int,
    @SerializedName("stat")
    var stat: GlobalModel
)