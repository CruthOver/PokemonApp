package com.example.pokemonapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.pokemonapp.data.models.MyPokemon
import com.example.pokemonapp.data.models.PokemonModel
import com.example.pokemonapp.data.repository.PokemonRepository
import com.example.pokemonapp.data.state.NetworkState
import com.example.pokemonapp.domain.model.MyPokemonDto
import com.example.pokemonapp.presentation.ui.theme.PokemonAppTheme
import com.example.pokemonapp.presentation.ui.view.PokemonDetailScreen
import com.example.pokemonapp.presentation.viewmodel.PokemonDetailViewModel

class PokemonDetailActivity: ComponentActivity() {
    private val pokemonRepository: PokemonRepository = PokemonRepository();
    private val pokemonDetailViewModel: PokemonDetailViewModel = PokemonDetailViewModel(pokemonRepository)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id: Int = Integer.parseInt(intent.getStringExtra("id").toString())
        pokemonDetailViewModel.getDetailPokemon(id)
        setContent {
            PokemonAppTheme(
                darkTheme = false,
                dynamicColor = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val networkState by pokemonDetailViewModel.state.collectAsState()
//                    val showDialog =  remember { mutableStateOf(false) }
//                    val snackBarHostState = remember { SnackbarHostState() }

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
                            val pokemon = (networkState as NetworkState.SUCCESS<PokemonModel>).pokemon
                            if (pokemon != null) {
                                val catchState by pokemonDetailViewModel.catchState.collectAsState()
                                when (catchState) {
                                    NetworkState.LOADING -> {
                                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                    is NetworkState.FAILURE -> {
                                        val message = (catchState as NetworkState.FAILURE).message
                                        Toast.makeText(this, "$message", Toast.LENGTH_LONG).show()
                                    }
                                    is NetworkState.SUCCESS<*> -> {
                                        val myPokemon = (catchState as NetworkState.SUCCESS<MyPokemonDto<MyPokemon>>).pokemon
                                        if (myPokemon != null)
                                            Toast.makeText(this, "${myPokemon.message}", Toast.LENGTH_LONG).show()
                                    }

                                    else -> {

                                    }
                                }

                                Box {
                                    PokemonDetailScreen(
                                        pokemonModel = pokemon,
                                        onNavigationBack = {
                                            finish()
                                        },

                                        catchPokemon = {
                                            val addPokemon: MyPokemon = MyPokemon(
                                                name = it.pokemonName,
                                                url = it.sprites.frontDefault,
                                                nickname = it.pokemonName,
                                                index = it.baseExperience,
                                            )
                                            pokemonDetailViewModel.catchPokemon(addPokemon)
                                        }
                                    )
                                }
                            } else {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                    Text(text = "Failed to Binding Data...",fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}