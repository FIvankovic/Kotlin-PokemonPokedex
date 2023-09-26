package com.filipaivankovic.lucijanpavic.pokemonpokedex.pokedex

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.filipaivankovic.lucijanpavic.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.AppDatabase
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.PokedexListEntry
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Pokemon
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.entite.Favorite
import com.filipaivankovic.lucijanpavic.pokemonpokedex.favorites.FavoriteViewModel
import com.filipaivankovic.lucijanpavic.pokemonpokedex.pokemondetail.PokemonTyping
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Globals
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Resource
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PokedexScreen(
    navController: NavController,
    viewModel: PokedexViewModel = hiltViewModel()
) {
    //Reset the topAppbar
    Globals.showTopAppBar = true

    //Lottie animation variables
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.heart))



    val favoriteAnimationProgress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = Globals.favoriteAnimationPlaying,
    )


    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if(Globals.favoriteAnimationPlaying){
            //Lottie Box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(99f),
                contentAlignment = Center
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(99f)
                ) {
                    //Animation File
                    LottieAnimation(
                        composition = composition,
                        progress = {favoriteAnimationProgress},
                        modifier = Modifier
                            .size(400.dp)
                    )
                }

            }//LottieBox
        }//If lottie



        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background.copy(alpha = 0.6f))
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp)
            ) {
                viewModel.searchPokemon(it)

            }
            Spacer(modifier = Modifier.height(1.dp))
            hwScaffold(
                navController = navController,
                viewModel = viewModel
            )
        }//Column
    }//Surface
}//PokedexScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun hwScaffold(
    navController: NavController,
    viewModel: PokedexViewModel = hiltViewModel()
) {

    Scaffold(
        content = {
            if (viewModel.pokemonList.value.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    content = {
                        items(items = viewModel.pokemonList.value) { pokemon ->
                            PokemonEntry(
                                navController = navController,
                                pokemon = pokemon,
                                viewModel = viewModel
                            )
                        }
                    }
                )
            } else if (viewModel.errorMessage.value.isNotEmpty()) {
                RetrySection(error = viewModel.errorMessage.value) {
                    viewModel.getPokemons()
                }
            }
        }
    )//Scaffold
}


@Composable
fun PokemonEntry(
    navController: NavController,
    pokemon: PokedexListEntry,
    viewModel: PokedexViewModel,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
) {

    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemon.pokemonName)
    }.value

    val favoritesByName = favoriteViewModel.fetchFavoritesByName(pokemon.pokemonName).observeAsState()



    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(5.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Globals.showTopAppBar = false
                    Globals.favoriteAnimationPlaying = false
                    navController.navigate(
                        "pokemonInfo/${pokemon.pokemonName}"
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
                    imageModel = { pokemon.imageUrl },
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
                        .padding(top = 8.dp, end = 16.dp)
                ) {
                    if (favoritesByName.value.isNullOrEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "image",
                            tint = Color.Red, modifier = Modifier
                                .size(30.dp)
                                .clickable(onClick = {

                                    Globals.favoriteAnimationPlaying = true
                                    //Favorite
                                    favoriteViewModel.insertFavorite(
                                        Favorite(
                                            imageUrl = pokemon.imageUrl,
                                            number = pokemon.number,
                                            pokemonName = pokemon.pokemonName
                                            //types = pokemonInfo.data?.types
                                        )
                                    )
                                    //Wait for a second before turning off the animation
                                    CoroutineScope(Dispatchers.Main).launch {
                                        delay(1000)
                                        Globals.favoriteAnimationPlaying = false
                                    }

                                })
                        )//Icon
                    }
                }
                Row {
                    Text(
                        text = "#${pokemon.number} ${pokemon.pokemonName.replaceFirstChar { it.uppercase() }}",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5
                    )

                }
                if (pokemonInfo is Resource.Success) {
                    pokemonInfo.data?.types.let { types ->
                        PokemonTyping(types = types ?: emptyList())
                    }
                }
            }//Column

        }//Row
    }//Card
}//PokemonEntry

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}

