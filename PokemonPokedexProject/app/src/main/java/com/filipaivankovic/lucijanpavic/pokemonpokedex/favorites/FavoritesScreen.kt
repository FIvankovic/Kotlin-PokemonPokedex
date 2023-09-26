package com.filipaivankovic.lucijanpavic.pokemonpokedex.favorites

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.entite.Favorite
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Globals
import com.skydoves.landscapist.glide.GlideImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    Globals.showTopAppBar = true
    Globals.favoriteAnimationPlaying = false
    val favoriteList = favoriteViewModel.fetchAllFavorites().observeAsState(arrayListOf())

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(5.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
    ) {
        LazyColumn(content = {
            items(items = favoriteList.value,
                itemContent = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                Globals.showTopAppBar = false
                                navController.navigate(
                                    "pokemonInfo/${it.pokemonName}"
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(MaterialTheme.colors.surface)
                                .padding()
                                .clip(shape = RoundedCornerShape(20.dp))
                        ) {
                            GlideImage(
                                imageModel = { it.imageUrl },
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(150.dp),
                                loading = {
                                    Box(modifier = Modifier.matchParentSize()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                },
                                failure = {
                                    Text("Image request failed")
                                },
                            )//GlideImage
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp,end = 16.dp)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "image",
                                    tint = Color.Red, modifier = Modifier
                                        .size(30.dp)
                                        .clickable(onClick = {
                                            favoriteViewModel.deleteFavoriteById(it.id)
                                        })
                                )
                            }
                            Row {
                                Text(
                                    text = "#${it.number} ${it.pokemonName.replaceFirstChar { it.uppercase() }}",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h5
                                )

                            }
                        }
                    }
            })
        })
    }
}
