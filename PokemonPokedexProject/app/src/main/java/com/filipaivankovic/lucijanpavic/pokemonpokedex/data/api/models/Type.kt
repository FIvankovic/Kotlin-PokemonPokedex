package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeX
)