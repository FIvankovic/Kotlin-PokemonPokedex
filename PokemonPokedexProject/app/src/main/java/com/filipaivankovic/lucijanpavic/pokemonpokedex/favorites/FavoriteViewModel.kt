package com.filipaivankovic.lucijanpavic.pokemonpokedex.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.entite.Favorite
import com.filipaivankovic.lucijanpavic.pokemonpokedex.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(appObj: Application) : AndroidViewModel(appObj) {
    private val favoriteRepository: FavoriteRepository = FavoriteRepository(appObj)

    fun fetchAllFavorites(): LiveData<List<Favorite>> {
        return favoriteRepository.readAllFavorite
    }
    fun fetchFavoritesByName(pokemonName: String): LiveData<List<Favorite>> {
        return favoriteRepository.readFavoriteByName(pokemonName)
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteRepository.insertFavorite(favorite = favorite)
        }
    }

    fun deleteFavoriteById(id: Int) {
        viewModelScope.launch {
            favoriteRepository.deleteFavoriteById(id)
        }
    }
}