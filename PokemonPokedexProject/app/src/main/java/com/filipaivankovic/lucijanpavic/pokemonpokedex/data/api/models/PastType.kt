package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models


import com.google.gson.annotations.SerializedName

data class PastType(
    @SerializedName("generation")
    val generation: Generation,
    @SerializedName("types")
    val types: List<Type>
)