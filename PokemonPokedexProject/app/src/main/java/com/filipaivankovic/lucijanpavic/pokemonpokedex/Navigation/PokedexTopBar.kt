package com.filipaivankovic.lucijanpavic.pokemonpokedex.pokedex

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.filipaivankovic.lucijanpavic.R
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Globals
import kotlinx.coroutines.CoroutineScope


@Composable
fun PokedexTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "Pokemon Pokedex",
                color = MaterialTheme.colors.onSurface
            )
        },
        actions ={
            Switch(
                checked = Globals.darkMode,
                onCheckedChange = { Globals.darkMode = it},
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = MaterialTheme.colors.onSurface,
                    checkedThumbColor = MaterialTheme.colors.onSurface
                )
            )
        },
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = Color.White,

    )


} // TopBar