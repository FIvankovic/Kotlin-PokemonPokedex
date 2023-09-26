package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.entite.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun fetchAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite where pokemon_name = :pokemonName")
    fun fetchFavoriteByName(pokemonName: String): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite where id = :id")
    suspend fun deleteFavoriteById(id: Int)
}