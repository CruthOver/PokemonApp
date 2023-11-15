package com.example.pokemonapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.models.MyPokemon
import com.example.pokemonapp.data.repository.PokemonRepository
import com.example.pokemonapp.data.state.NetworkState
import com.example.pokemonapp.domain.model.MyPokemonDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class MyPokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    val state = MutableStateFlow<NetworkState>(NetworkState.START)
    val stateDel = MutableStateFlow<NetworkState>(NetworkState.START)

    init {
        getMyPokemon()
    }

    fun getMyPokemon() = viewModelScope.launch {
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

    fun releaseMyPokemon(id: Int) = viewModelScope.launch {
        stateDel.value = NetworkState.LOADING
        try {
            val response = withContext(Dispatchers.IO) { pokemonRepository.releasePokemon(id) }
            Log.println(Log.ASSERT, "MainActivity", "Hasil : ${response.body().toString()}")
//            Debug.logStack("MainActivity", "Hasil : ${response.body().toString()}", Log.ASSERT)
            stateDel.value = NetworkState.SUCCESS<MyPokemonDto<ResponseBody>>(response.body())
        } catch (e: Exception) {
            Log.println(Log.ASSERT, "MainActivity", "Hasilnya : ${e.localizedMessage}")
            stateDel.value = NetworkState.FAILURE(e.localizedMessage)
        }
    }
}