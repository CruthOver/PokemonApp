package com.example.pokemonapp.presentation.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokemonapp.R
import com.example.pokemonapp.data.models.Move
import com.example.pokemonapp.data.models.PokemonModel
import com.example.pokemonapp.data.models.Stats
import com.example.pokemonapp.presentation.component.AppBar
import com.example.pokemonapp.presentation.component.CarouselComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(onNavigationBack: (() -> Unit)?, pokemonModel: PokemonModel,
                        catchPokemon: ((PokemonModel) -> Unit)) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    var types: String = ""
    for (type in pokemonModel.types) {
        types += ", "
        types += type.type.name
    }

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        AppBar(
            title = "Detail Pokemon",
            onNavigationBack = onNavigationBack,
            scrollBehavior = scrollBehavior
        )
    }, bottomBar = {
            Button(onClick = { catchPokemon(pokemonModel) }, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Catch", fontSize = 18.sp)
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn (modifier = Modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                item {
                    var images = listOf(
                        pokemonModel.sprites.frontDefault,
                        pokemonModel.sprites.backDefault,
                        pokemonModel.sprites.frontShiny,
                        pokemonModel.sprites.backShiny,
                    )
                    ImageSlider(images)
                }
                item {
                    ItemRow(label = "Name", value = pokemonModel.pokemonName)
                }
                item {
                    ItemRow(label = "Weight", value = pokemonModel.weight.toString())
                }
                item {
                    ItemRow(label = "Height", value = pokemonModel.height.toString())
                }
                item {
                    ItemRow(label = "Base Experience", value = pokemonModel.baseExperience.toString())
                }
                item {
                    ItemRow(label = "Species", value = pokemonModel.species.name)
                }
                item {
                    ItemRow(label = "Type", value = types)
                }
                item {
                    ItemStats(label = "Stats", stats = pokemonModel.stats)
                }
                item {
                    ItemMove(label = "Moves", moves = pokemonModel.moves)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(images:List<String>) {
    CarouselComponent(
        itemsCount = images.size,
        itemContent = {
            Image(
                painter = // Optional: Add image transformations
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = images[it])
                        .apply(block = fun ImageRequest.Builder.() {
                            // Optional: Add image transformations
                            placeholder(R.drawable.ic_launcher_foreground)
                        }).build()
                ),
                contentDescription = "Coil Image",
                modifier = Modifier
                    .size(300.dp)
                    .height(300.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
        }
    )
}

@Composable
fun ItemRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
           text = label,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ItemMove(
    label: String,
    moves: List<Move>,
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = label,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            buildAnnotatedString {
                moves.forEach {
                    withStyle(style = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))) {
                        append("\u2022")
                        append("\t\t")
                        append(it.move.name)
                    }
                }
            }
        )
    }
}

@Composable
fun ItemStats(
    label: String,
    stats: List<Stats>,
) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = label,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            buildAnnotatedString {
                stats.forEach {
                    withStyle(style = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))) {
                        append("\u2022")
                        append("\t\t")
                        append(it.stat.name)
                        append(" : ")
                        append(it.baseStat.toString())
                    }
                }
            }
        )
    }
}