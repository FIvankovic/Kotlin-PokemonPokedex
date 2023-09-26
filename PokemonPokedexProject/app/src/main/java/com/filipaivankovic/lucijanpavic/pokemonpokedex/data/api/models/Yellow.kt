package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models


import com.google.gson.annotations.SerializedName

data class Yellow(
    @SerializedName("back_default")
    val backDefault: Any,
    @SerializedName("back_gray")
    val backGray: Any,
    @SerializedName("back_transparent")
    val backTransparent: Any,
    @SerializedName("front_default")
    val frontDefault: Any,
    @SerializedName("front_gray")
    val frontGray: Any,
    @SerializedName("front_transparent")
    val frontTransparent: Any
)