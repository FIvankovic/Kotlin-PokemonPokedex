package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api

import android.annotation.SuppressLint
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Ability
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityDetails.AbilityDetails
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Pokemon
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.PokemonList
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton


interface APIService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon

    @GET("ability/{number}")
    suspend fun getAbilityInfo(
        @Path("number") number: String
    ): AbilityDetails


}//APIService