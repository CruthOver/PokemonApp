package com.example.pokemonapp.data.models

import com.google.gson.annotations.SerializedName

class Type(
    @SerializedName("slot")
    var slot: Int,
    @SerializedName("type")
    var type: GlobalModel
)