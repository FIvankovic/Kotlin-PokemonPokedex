package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityDetails


import com.google.gson.annotations.SerializedName

data class Generation(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)