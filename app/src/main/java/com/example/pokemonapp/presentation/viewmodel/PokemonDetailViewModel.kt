package com.example.pokemonapp.presentation.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.data.models.MyPokemon
import com.example.pokemonapp.data.models.PokemonModel
import com.example.pokemonapp.data.repository.PokemonRepository
import com.example.pokemonapp.data.state.NetworkState
import com.example.pokemonapp.domain.model.MyPokemonDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailViewModel(private val pokemonRepository: PokemonRepository) : ViewModel()  {
    val state = MutableStateFlow<NetworkState>(NetworkState.START)
    val catchState = MutableStateFlow<NetworkState>(NetworkState.START)

    fun getDetailPokemon(id: Int) = viewModelScope.launch {
        state.value = NetworkState.LOADING
        try {
            val response = withContext(Dispatchers.IO) { pokemonRepository.getDetailPokemon(id) }
            Log.println(Log.ASSERT, "MainActivity", "Hasil : ${response.body().toString()}")
//            Debug.logStack("MainActivity", "Hasil : ${response.body().toString()}", Log.ASSERT)
            state.value = NetworkState.SUCCESS<PokemonModel>(response.body())
        } catch (e: Exception) {
            Log.println(Log.ASSERT, "MainActivity", "Hasilnya : ${e.localizedMessage}")
            state.value = NetworkState.FAILURE(e.localizedMessage)
        }
    }

    fun catchPokemon(myPokemon: MyPokemon) = viewModelScope.launch {
        catchState.value = NetworkState.LOADING
        try {
            val response = withContext(Dispatchers.IO) { pokemonRepository.catchPokemon(myPokemon) }
            Log.println(Log.ASSERT, "MainActivity", "Catch : ${response.body().toString()}")
            catchState.value = NetworkState.SUCCESS<MyPokemonDto<MyPokemon>>(response.body())
        } catch (e: Exception) {
            Log.println(Log.ASSERT, "MainActivity", "Catch Failed : ${e.localizedMessage}")
            catchState.value = NetworkState.FAILURE(e.localizedMessage)
        }
    }
}