package com.filipaivankovic.lucijanpavic.pokemonpokedex.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.AbilityDetails.AbilityDetails
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Move
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Globals
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Resource

@Composable
fun PokemonMoves(
    moves: List<Move>
) {
    //For diplaying/hiding the dialog alert
    var showDialog = remember {
        mutableStateOf(false)
    }

    val background = if (Globals.darkMode) {
        Color.DarkGray
    } else {
        Color.LightGray
    }


    //Show dialog
    if (showDialog.value) {
        // Show the dialog with the ability details
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(
                    text = "Learnable Moves"
                )
            },
            text = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    items(moves) { move ->
                        Text(
                            text = move.move.name.replaceFirstChar { it.uppercase() }
                        )
                    }
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


    Row() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(3f)
                .padding(20.dp)
                .shadow(2.dp)
                .height(30.dp)
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(background)
                .clickable {
                    showDialog.value = true
                }
        ) {
            Text(
                text = "Moves List",
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                style = MaterialTheme.typography.button
            )
        }//Box
    }//Row
}//PokemonMoves