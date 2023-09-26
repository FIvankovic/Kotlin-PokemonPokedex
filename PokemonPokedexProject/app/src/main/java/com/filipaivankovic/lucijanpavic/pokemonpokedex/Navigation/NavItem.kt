package com.filipaivankovic.lucijanpavic.pokemonpokedex.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(
    var route: String,
    var vector: ImageVector,
    var title: String
){
    object PokedexScreen: NavItem("Pokedex", Icons.Outlined.List, "Pokedex Screen")
    object FavoritesScreen: NavItem("Favorites", Icons.Default.Favorite, "Favorites Screen")
}