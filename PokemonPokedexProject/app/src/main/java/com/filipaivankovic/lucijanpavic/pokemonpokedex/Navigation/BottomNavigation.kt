package com.filipaivankovic.lucijanpavic.pokemonpokedex.Navigation

import androidx.compose.material.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(modifier: Modifier = Modifier, navController: NavController) {

    val screens = listOf(
        NavItem.PokedexScreen,
        NavItem.FavoritesScreen
    )

    BottomNavigation(
        modifier = Modifier,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.vector,
                        contentDescription = "Icon"
                    )
                },

                label = { Text(text = screen.route) },
                selected = currentRoute == screen.route,

                onClick = {
                    navController.navigate(screen.route){
                        launchSingleTop = true
                    }
                }

            )
        }
    }
}//Bottom Bar