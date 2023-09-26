package com.filipaivankovic.lucijanpavic.pokemonpokedex.pokemondetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityDetails.AbilityDetails
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Pokemon
import com.filipaivankovic.lucijanpavic.pokemonpokedex.repository.PokemonRepository
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel(){


    var selectedAbilityNumber by mutableStateOf("1")

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }

    suspend fun getAbilityDetails(abilityNumber: String): Resource<AbilityDetails>{
        return  repository.getAbilityDetails(abilityNumber)
    }

}