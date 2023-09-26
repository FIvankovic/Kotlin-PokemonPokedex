package com.filipaivankovic.lucijanpavic.pokemonpokedex.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Ability
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityDetails.AbilityDetails
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityX
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Pokemon
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Globals
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Resource
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.parseTypeToColor

@Composable
fun PokemonAbilities(
    abilities: List<Ability>,
    padding: Dp = 5.dp,
    height: Dp = 35.dp,
    viewModel: PokemonDetailViewModel
) {
    //For diplaying/hiding the dialog alert
    var showDialog = remember {
        mutableStateOf(false)
    }

    //Change bg color depending on dark/light mode
    val background = if (Globals.darkMode) {
        Color.DarkGray
    } else {
        Color.LightGray
    }

    // Get the ability numbers
    val abilityNumbers = remember {
        mutableListOf<String>().apply {
            for (ability in abilities) {
                val url = ability.ability.url.toString()
                val parts = url?.split("/")
                val number = parts?.getOrNull(parts.size - 2).toString()
                number?.let { add(it) }
            }
        }
    }

    //Show dialog
    if (showDialog.value) {
        //API CALL
        val abilityInfo =
            produceState<Resource<AbilityDetails>>(initialValue = Resource.Loading()) {
                value = viewModel.getAbilityDetails(viewModel.selectedAbilityNumber)
            }.value

        // Show the dialog with the ability details
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(
                    text = abilityInfo.data?.name.toString().replaceFirstChar { it.uppercase() }
                )
            },
            text = {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .height(250.dp)
                ) {
                    Text(
                        text = abilityInfo.data?.effectEntries?.getOrNull(1)?.effect ?: "Shit"
                    )
                }

            },
            confirmButton = {
                TextButton(
                    onClick = { showDialog.value = false }
                ) {
                    Text("Ok")
                }
            }
        )
    }//dialog condition

    //VISUALS
    Text(
        text = "Abilities",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(background)
            .padding(20.dp)
    ) {
        for ((index, ability) in abilities.withIndex()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = padding)
                    .shadow(2.dp)
                    .height(height)
                    .background(MaterialTheme.colors.surface)
                    .clickable {
                        showDialog.value = true
                        viewModel.selectedAbilityNumber = abilityNumbers[index]
                    }
            ) {
                Text(
                    text = ability.ability.name.replaceFirstChar {
                        it.uppercase()
                    },
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center
                )
            }//Box
        }//For end
    }//Row
}

