package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
