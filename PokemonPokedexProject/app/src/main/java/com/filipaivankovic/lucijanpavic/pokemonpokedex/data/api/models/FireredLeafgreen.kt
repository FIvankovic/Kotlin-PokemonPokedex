package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models


import com.google.gson.annotations.SerializedName

data class FireredLeafgreen(
    @SerializedName("back_default")
    val backDefault: Any,
    @SerializedName("back_shiny")
    val backShiny: Any,
    @SerializedName("front_default")
    val frontDefault: Any,
    @SerializedName("front_shiny")
    val frontShiny: Any
)