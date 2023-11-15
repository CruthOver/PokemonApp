package com.example.pokemonapp.presentation.ui.view

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pokemonapp.R
import com.example.pokemonapp.data.models.PokemonModel
import com.example.pokemonapp.data.state.NetworkState
import com.example.pokemonapp.domain.model.PokemonDto
import com.example.pokemonapp.presentation.viewmodel.MainViewModel
import com.example.pokemonapp.presentation.viewmodel.PokemonViewModel

@Composable
fun PokemonScreen(viewModel: MainViewModel, pokemonViewModel: PokemonViewModel, onItemClick: (PokemonModel) -> Unit) {
    val networkState by pokemonViewModel.state.collectAsState()
    when (networkState) {
        NetworkState.START -> {
        }
        NetworkState.LOADING -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        is NetworkState.FAILURE -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = "Something went wrong...",fontSize = 16.sp)
            }
        }
        is NetworkState.SUCCESS<*> -> {
            val pokemon = (networkState as NetworkState.SUCCESS<PokemonDto>).pokemon
            PokemonList(viewModel, pokemon!!, onItemClick = onItemClick)
        }
    }
}

@Composable
fun PokemonList(viewModel: MainViewModel, pokemons: PokemonDto, onItemClick: (PokemonModel) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().draggable(
        state = viewModel.dragState.value!!,
        orientation = Orientation.Horizontal,
        onDragStarted = {  },
        onDragStopped = {
            viewModel.updateTabIndexBasedOnSwipe()
        }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(pokemons.results.size) { i ->
                PokemonRow(pokemons.results[i], onItemClick)
            }
        }
    }
}

@Composable
fun PokemonRow(pokemons: PokemonModel, onItemClick: (PokemonModel) -> Unit) {
    val fileName = pokemons.url.split("/")[6]
    Row(
        modifier = Modifier
            .clickable { onItemClick(pokemons) }
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = // Optional: Add image transformations
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${fileName}.png")
                    .apply(block = fun ImageRequest.Builder.() {
                        // Optional: Add image transformations
                        placeholder(R.drawable.ic_launcher_foreground)
                    }).build()
            ),
            contentDescription = "Coil Image",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = pokemons.pokemonName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}