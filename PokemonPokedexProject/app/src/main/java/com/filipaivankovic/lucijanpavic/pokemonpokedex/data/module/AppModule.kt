package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.module

import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.APIService
import com.filipaivankovic.lucijanpavic.pokemonpokedex.repository.PokemonRepository
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: APIService
    ) = PokemonRepository(api)

    @Singleton @Provides
    fun providePokeApi(): APIService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIService::class.java)
    }
}