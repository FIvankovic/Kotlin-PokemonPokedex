package com.filipaivankovic.lucijanpavic.pokemonpokedex.pokedex

import android.graphics.Bitmap
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.PokedexListEntry
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Pokemon
import com.filipaivankovic.lucijanpavic.pokemonpokedex.repository.PokemonRepository
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Constants.PAGE_SIZE
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var curPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
    var errorMessage = mutableStateOf("")
    var endReached = mutableStateOf(false)
    private var cachedPokemonList = listOf<PokedexListEntry>()
    private var searchStarting = true
    var searching = mutableStateOf(false)
    var loading = mutableStateOf(false)

    init {
        getPokemons()
    }

    fun searchPokemon(query: String) {
        val listToSearch = if(searchStarting) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                searching.value = false
                searchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }
            if(searchStarting) {
                cachedPokemonList = pokemonList.value
                searchStarting = false
            }
            pokemonList.value = results
            searching.value = true
        }
    }

    fun getPokemons() {
        viewModelScope.launch {
            loading.value = true
            val result = repository.getPokemonList(PAGE_SIZE, curPage * PAGE_SIZE)
            when(result) {
                is Resource.Success -> {
                    endReached.value = curPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { _, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png"
                        PokedexListEntry(entry.name, url, number.toInt())
                    }
                    curPage++

                    errorMessage.value = ""
                    loading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    loading.value = false
                }
                is Resource.Loading -> {
                    loading.value = false
                }
            }
        }
    }//getPokemons
    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }
}//PokedexViewModel
