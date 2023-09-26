package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityDetails


import com.google.gson.annotations.SerializedName

data class EffectEntry(
    @SerializedName("effect")
    val effect: String,
    @SerializedName("language")
    val language: Language
)