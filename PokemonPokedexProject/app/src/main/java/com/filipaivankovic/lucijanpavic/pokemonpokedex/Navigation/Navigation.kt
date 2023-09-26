package com.filipaivankovic.lucijanpavic.pokemonpokedex.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.filipaivankovic.lucijanpavic.pokemonpokedex.favorites.FavoriteScreen
import com.filipaivankovic.lucijanpavic.pokemonpokedex.pokedex.PokedexScreen
import com.filipaivankovic.lucijanpavic.pokemonpokedex.pokedex.PokemonDetailScreen


@Composable
fun Navigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = NavItem.PokedexScreen.route)
    {
        composable(NavItem.PokedexScreen.route){
            PokedexScreen(navController = navController)
        }
        composable(NavItem.FavoritesScreen.route){
            FavoriteScreen(navController = navController)
        }
        composable(
            "pokemonInfo/{pokemonName}",
            arguments = listOf(

                navArgument("pokemonName") {
                    type = NavType.StringType
                }
            )
        ) {

            val pokemonName = remember {
                it.arguments?.getString("pokemonName")
            }
            PokemonDetailScreen(
                pokemonName = pokemonName?: "",
                navController = navController
            )
        }
    }//Navhost
}