package com.example.pokemonapp.presentation.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemonapp.data.models.MyPokemon
import com.example.pokemonapp.data.models.PokemonModel
import com.example.pokemonapp.presentation.viewmodel.MainViewModel
import com.example.pokemonapp.presentation.viewmodel.MyPokemonViewModel
import com.example.pokemonapp.presentation.viewmodel.PokemonViewModel

@Preview(showBackground = true)
@Composable
fun TabBarView(viewModel: MainViewModel, pokemonViewModel: PokemonViewModel, onItemClick: (PokemonModel) -> Unit,
               myPokemonViewModel: MyPokemonViewModel, onItemClickMyPokemon: (MyPokemon) -> Unit) {
    val tabIndex = viewModel.tabIndex.observeAsState()
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex.value!!) {
            viewModel.tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex.value!! == index,
                    onClick = { viewModel.updateTabIndex(index) },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.Home, contentDescription = null)
                            1 -> Icon(imageVector = Icons.Default.Info, contentDescription = null)
                            2 -> Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                        }
                    }
                )
            }
        }

        when (tabIndex.value) {
            0 -> PokemonScreen(viewModel = viewModel, pokemonViewModel = pokemonViewModel, onItemClick = onItemClick)
            1 -> MyPokemonScreen(viewModel = viewModel, myPokemonViewModel = myPokemonViewModel, onConfirm = onItemClickMyPokemon)
        }
    }
}