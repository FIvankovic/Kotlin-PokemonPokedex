package com.filipaivankovic.lucijanpavic.pokemonpokedex.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.AppDatabase
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.dao.FavoriteDao
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.entite.Favorite

class FavoriteRepository(application: Application) {
    private var favoriteDao: FavoriteDao

    init {
        val database = AppDatabase.getDatabase(application)
        favoriteDao = database.favoriteDao()
    }

    val readAllFavorite: LiveData<List<Favorite>> = favoriteDao.fetchAllFavorites()

    fun readFavoriteByName(pokemonName: String): LiveData<List<Favorite>> {
        return favoriteDao.fetchFavoriteByName(pokemonName)
    }

    suspend fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }

    suspend fun deleteFavoriteById(id: Int) {
        favoriteDao.deleteFavoriteById(id)
    }
}