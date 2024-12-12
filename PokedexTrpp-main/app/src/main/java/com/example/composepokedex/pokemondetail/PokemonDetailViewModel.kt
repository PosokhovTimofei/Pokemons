package com.example.composepokedex.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.composepokedex.data.remote.responses.Pokemon
import com.example.composepokedex.repository.PokemonRepository
import com.example.composepokedex.util.Resourse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    suspend fun getPokemonInfo(pokemonName: String): Resourse<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }
}