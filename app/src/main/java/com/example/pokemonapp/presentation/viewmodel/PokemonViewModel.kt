package com.example.pokemonapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.models.MyPokemon
import com.example.pokemonapp.data.repository.PokemonRepository
import com.example.pokemonapp.data.state.NetworkState
import com.example.pokemonapp.domain.model.MyPokemonDto
import com.example.pokemonapp.domain.model.PokemonDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    val state = MutableStateFlow<NetworkState>(NetworkState.START)

    init {
        getPokemon()
    }

    private fun getPokemon() = viewModelScope.launch {
        state.value = NetworkState.LOADING
        try {
            val response = withContext(Dispatchers.IO) { pokemonRepository.getPokemon() }
            Log.println(Log.ASSERT, "MainActivity", "Hasil : ${response.body().toString()}")
//            Debug.logStack("MainActivity", "Hasil : ${response.body().toString()}", Log.ASSERT)
            state.value = NetworkState.SUCCESS<PokemonDto>(response.body())
        } catch (e: Exception) {
            Log.println(Log.ASSERT, "MainActivity", "Hasilnya : ${e.localizedMessage}")
            state.value = NetworkState.FAILURE(e.localizedMessage)
        }
    }

    private fun getMyPokemon() = viewModelScope.launch {
        state.value = NetworkState.LOADING
        try {
            val response = withContext(Dispatchers.IO) { pokemonRepository.getMyPokemon() }
            Log.println(Log.ASSERT, "MainActivity", "Hasil : ${response.body().toString()}")
//            Debug.logStack("MainActivity", "Hasil : ${response.body().toString()}", Log.ASSERT)
            state.value = NetworkState.SUCCESS<MyPokemonDto<List<MyPokemon>>>(response.body())
        } catch (e: Exception) {
            Log.println(Log.ASSERT, "MainActivity", "Hasilnya : ${e.localizedMessage}")
            state.value = NetworkState.FAILURE(e.localizedMessage)
        }
    }
}