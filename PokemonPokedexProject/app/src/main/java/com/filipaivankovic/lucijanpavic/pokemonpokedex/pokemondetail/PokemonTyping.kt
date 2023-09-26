package com.filipaivankovic.lucijanpavic.pokemonpokedex.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Ability
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Pokemon
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Type
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.parseTypeToColor


@Composable
fun PokemonTyping(
    types: List<Type>,
    height: Dp = 25.dp,
    padding: Dp = 5.dp
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ){
        for (type in types){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = padding)
                    .shadow(5.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(parseTypeToColor(type))
                    .height(height)
            ){
                Text(
                    text = type.type.name.replaceFirstChar {
                        it.uppercase()
                    },
                    color = Color.White,

                )
            }
        }
    }
}
