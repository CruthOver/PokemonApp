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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.pokemonapp.data.models.MyPokemon
import com.example.pokemonapp.data.state.NetworkState
import com.example.pokemonapp.domain.model.MyPokemonDto
import com.example.pokemonapp.presentation.component.BottomSheetComponent
import com.example.pokemonapp.presentation.component.DialogConfirmationComponent
import com.example.pokemonapp.presentation.viewmodel.MainViewModel
import com.example.pokemonapp.presentation.viewmodel.MyPokemonViewModel
import okhttp3.ResponseBody

@Composable
fun MyPokemonScreen(viewModel: MainViewModel, myPokemonViewModel: MyPokemonViewModel, onConfirm: (MyPokemon) -> Unit) {
    val networkState by myPokemonViewModel.state.collectAsState()
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
            val pokemon = (networkState as NetworkState.SUCCESS<MyPokemonDto<List<MyPokemon>>>).pokemon
            MyPokemonList(viewModel, pokemon!!.data!!, onConfirm = onConfirm )
        }
    }
}

@Composable
fun MyPokemonList(viewModel: MainViewModel, pokemons: List<MyPokemon>, onConfirm: (MyPokemon) -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .draggable(
            state = viewModel.dragState.value!!,
            orientation = Orientation.Horizontal,
            onDragStarted = { },
            onDragStopped = {
                viewModel.updateTabIndexBasedOnSwipe()
            }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(pokemons.size) { i ->
                PokemonRow(pokemons[i], onConfirm = onConfirm)
            }
        }
    }
}

@Composable
fun PokemonRow(myPokemon: MyPokemon, onConfirm: (MyPokemon) -> Unit) {
    val showSheet = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    if (showSheet.value) {
        BottomSheetComponent(
            myPokemon = myPokemon,
            onDismiss = {
                showSheet.value = false
            },
            onDelete = {
                showSheet.value = false
                showDialog.value = true
            },
            onEdit = {
                showSheet.value = false
            },
        )
    }


    if (showDialog.value) {
        DialogConfirmationComponent(
            myPokemon = myPokemon,
            onDismiss = {
                showDialog.value = false
            },
            onConfirm = {
                showDialog.value = false
                onConfirm(myPokemon)
            }
        )
    }
    Row(
        modifier = Modifier
            .clickable {
                showSheet.value = true
            }
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = // Optional: Add image transformations
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = myPokemon.url)
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
                text = myPokemon.nickname,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = myPokemon.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}