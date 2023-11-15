package com.example.pokemonapp.data.state

sealed class NetworkState {
    object START : NetworkState()
    object LOADING : NetworkState()
    data class SUCCESS<T>(val pokemon: T?) : NetworkState()
    data class FAILURE(val message: String?) : NetworkState()
}