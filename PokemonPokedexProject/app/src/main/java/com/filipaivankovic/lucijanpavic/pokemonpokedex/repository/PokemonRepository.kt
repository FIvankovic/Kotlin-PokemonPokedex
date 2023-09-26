package com.filipaivankovic.lucijanpavic.pokemonpokedex.repository

import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.APIService
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.PokedexListEntry
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityDetails.AbilityDetails
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Pokemon
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.PokemonList
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Constants
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: APIService
) {

        suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>{
            val response = try{
                api.getPokemonList(limit, offset)
            } catch(e: Exception){
                return Resource.Error("Error occurred getting the Pokemon")
            }
            return Resource.Success(response)
        }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch(e: Exception) {
            return Resource.Error("Error occurred getting Pokemon info")
        }
        return Resource.Success(response)
    }

    suspend fun getAbilityDetails(abilityNumber: String):Resource<AbilityDetails>{
        val response = try{
            api.getAbilityInfo(abilityNumber)
        } catch (e: Exception){
            return Resource.Error("Error occured getting Ability details.")
        }
        return Resource.Success(response)
    }

}