package com.filipaivankovic.lucijanpavic.pokemonpokedex.data.entite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Type

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "number")
    val number: Int,
    @ColumnInfo(name = "pokemon_name")
    val pokemonName: String,
)
