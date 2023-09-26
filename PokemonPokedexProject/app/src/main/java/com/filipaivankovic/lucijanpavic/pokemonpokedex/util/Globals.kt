package com.filipaivankovic.lucijanpavic.pokemonpokedex.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

object Globals {
    var darkMode by mutableStateOf(false)
    var showTopAppBar by mutableStateOf(true)
    var favoriteAnimationPlaying by mutableStateOf(false)
}