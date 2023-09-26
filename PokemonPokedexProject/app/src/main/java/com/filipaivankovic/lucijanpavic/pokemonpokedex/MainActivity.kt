package com.filipaivankovic.lucijanpavic.pokemonpokedex

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.filipaivankovic.lucijanpavic.pokemonpokedex.Navigation.BottomNavigation
import com.filipaivankovic.lucijanpavic.pokemonpokedex.pokedex.PokedexScreen
import com.filipaivankovic.lucijanpavic.pokemonpokedex.pokedex.PokedexTopBar
import com.filipaivankovic.lucijanpavic.pokemonpokedex.pokedex.PokemonDetailScreen
import com.filipaivankovic.lucijanpavic.pokemonpokedex.Navigation.Navigation
import com.filipaivankovic.lucijanpavic.pokemonpokedex.repository.ui.theme.AppTheme
import com.filipaivankovic.lucijanpavic.pokemonpokedex.repository.ui.theme.PokemonPokedexTheme
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Globals
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonPokedexTheme(Globals.darkMode) {
                MainScreen()
            }//PokemonPokedexTheme
        }//setContent
    }//onCreate
}//MainActivity

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            if (Globals.showTopAppBar) {
                PokedexTopBar()
            }
        },
        scaffoldState = scaffoldState,
        bottomBar = { BottomNavigation(navController = navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, innerPadding.calculateBottomPadding())
        ) {
            Navigation(navController = navController)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonPokedexTheme {
    }
}