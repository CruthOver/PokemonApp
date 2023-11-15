package com.example.pokemonapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.pokemonapp.data.models.MyPokemon
import com.example.pokemonapp.data.repository.PokemonRepository
import com.example.pokemonapp.data.state.NetworkState
import com.example.pokemonapp.domain.model.MyPokemonDto
import com.example.pokemonapp.presentation.component.BottomSheetComponent
import com.example.pokemonapp.presentation.component.DialogConfirmationComponent
import com.example.pokemonapp.presentation.ui.theme.PokemonAppTheme
import com.example.pokemonapp.presentation.ui.view.MyPokemonList
import com.example.pokemonapp.presentation.ui.view.TabBarView
import com.example.pokemonapp.presentation.viewmodel.MainViewModel
import com.example.pokemonapp.presentation.viewmodel.MyPokemonViewModel
import com.example.pokemonapp.presentation.viewmodel.PokemonViewModel
import okhttp3.ResponseBody

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val pokemonRepository: PokemonRepository = PokemonRepository();
    private val pokemonViewModel: PokemonViewModel = PokemonViewModel(pokemonRepository)
    private val myPokemonViewModel: MyPokemonViewModel = MyPokemonViewModel(pokemonRepository)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val stateDel by myPokemonViewModel.stateDel.collectAsState()
            PokemonAppTheme(
                darkTheme = false,
                dynamicColor = false
            ) {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    TabBarView(viewModel = viewModel, pokemonViewModel = pokemonViewModel, onItemClick = {
                        val i = Intent(this, PokemonDetailActivity::class.java)
                        val id = it.url.split("/")[6]
                        i.putExtra("id", id)
                        startActivity(i)
                    }, myPokemonViewModel = myPokemonViewModel,
                        onItemClickMyPokemon = {
                            myPokemonViewModel.releaseMyPokemon(it.id!!)
                            when (stateDel) {
                                NetworkState.START -> {
                                }
                                NetworkState.LOADING -> {
                                }
                                is NetworkState.FAILURE -> {
                                    Toast.makeText(this, "Failed to Relase Pokemon", Toast.LENGTH_LONG).show()
                                }
                                is NetworkState.SUCCESS<*> -> {
                                    val response = (stateDel as NetworkState.SUCCESS<MyPokemonDto<ResponseBody>>).pokemon
                                    if (response?.success == true) {
                                        myPokemonViewModel.state.value = NetworkState.START
                                        myPokemonViewModel.getMyPokemon()
                                        Toast.makeText(this, "${response.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }

                        })
                }
            }
        }
    }
}