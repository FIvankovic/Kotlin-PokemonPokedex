package com.filipaivankovic.lucijanpavic.pokemonpokedex.pokedex


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.filipaivankovic.lucijanpavic.pokemonpokedex.data.api.models.Pokemon
import com.filipaivankovic.lucijanpavic.pokemonpokedex.pokemondetail.*
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Globals
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.Resource
import com.filipaivankovic.lucijanpavic.pokemonpokedex.util.parseTypeToColor
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    Globals.showTopAppBar = false
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value

    var isShiny = remember {
        mutableStateOf(false)
    }

    //CALCULATING THE BACKGROUND COLORS
    var color1 = MaterialTheme.colors.surface
    var color2 = MaterialTheme.colors.surface

    if (pokemonInfo is Resource.Success) {
        pokemonInfo.data?.types.let { types ->
            if (types?.get(0) != null) {
                color1 = parseTypeToColor(types.get(0))
            }
            if (types?.size ?: 0 > 1) {
                if (types != null) {
                    color2 = parseTypeToColor(types.get(1))
                }

            }
        }
    }//if statement


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        color1,
                        color2
                    )
                )
            )
            .padding(bottom = 16.dp)
    ) {

        //Shiny Button
        Button(
            onClick = {
                isShiny.value = !isShiny.value
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(color = Color.Transparent)
                .padding(end = 5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                modifier = Modifier
                    .size(24.dp)
            )
        }//Button
        PokemonDetailBody(
            pokemonInfo = pokemonInfo,
            viewModel = viewModel,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )

        //ImageBox


        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (pokemonInfo is Resource.Success) {
                pokemonInfo.data?.sprites?.let {
                    var pokeImage = pokemonInfo.data.sprites.other.officialArtwork.frontDefault
                    if (isShiny.value) {
                        pokeImage = pokemonInfo.data.sprites.other.officialArtwork.frontShiny
                    }
                    GlideImage(
                        imageModel = {
                            pokeImage
                        },
                        modifier = Modifier
                            .size(pokemonImageSize)
                            .offset(y = topPadding)
                    )
                }
            }
        }//Box
    }
}

@Composable
fun PokemonDetailBody(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    viewModel: PokemonDetailViewModel,
    navController: NavController
) {
    when (pokemonInfo) {
        is Resource.Success -> {
            PokemonDetailSection(
                pokemonInfo = pokemonInfo.data!!,
                viewModel = viewModel,
                navController = navController,
                modifier = modifier
                    .offset(y = (-20).dp)
            )
        }
        is Resource.Error -> {
            Text(
                text = pokemonInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun PokemonDetailSection(
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier,
    viewModel: PokemonDetailViewModel,
    navController: NavController
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
            .padding(bottom = 180.dp) //Increase this as needed
    ) {
        Text(
            text = "#${pokemonInfo.id} ${pokemonInfo.name.replaceFirstChar { it.uppercase() }}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onSurface
        )
        PokemonTyping(types = pokemonInfo.types, height = 35.dp, padding = 8.dp)
        BaseStats(pokemonInfo = pokemonInfo)
        Spacer(modifier = Modifier.height(20.dp))
        PokemonAbilities(abilities = pokemonInfo.abilities, viewModel = viewModel)
        Spacer(modifier = Modifier.height(20.dp))
        PokemonMoves(moves = pokemonInfo.moves)
        Spacer(modifier = Modifier.height(50.dp))
        PokemonDataSection(pokemonWeight = pokemonInfo.weight, pokemonHeight = pokemonInfo.height)
        Spacer(modifier = Modifier.height(100.dp))
        NavigationButtonRow(navController = navController, pokemonInfo = pokemonInfo)
    }//Column
}//PokemonDetailSection

@Composable
fun NavigationButtonRow(
    navController: NavController,
    pokemonInfo: Pokemon
){
    Row() {
        //Backwards Button
        Button(
            onClick = {
                if(pokemonInfo.id != 1){
                    navController.navigate(
                        "pokemonInfo/${pokemonInfo.id-1}"
                    )
                }
            },
            modifier = Modifier
                .background(color = Color.Transparent)
                .padding(end = 5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Backwards",
                modifier = Modifier
                    .size(24.dp)
            )
        }//Button
        Spacer(modifier = Modifier.width(50.dp))
        //Forward Button
        Button(
            onClick = {
                navController.navigate(
                    "pokemonInfo/${pokemonInfo.id+1}"
                )
            },
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Forward",
                modifier = Modifier
                    .size(24.dp)
            )
        }//Button
    }//ButtonRow
}//NavigationButtonRow

