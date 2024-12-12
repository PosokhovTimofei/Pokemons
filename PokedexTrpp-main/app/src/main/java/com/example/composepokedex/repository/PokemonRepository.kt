package com.example.composepokedex.repository

import com.example.composepokedex.data.remote.PokeApi
import com.example.composepokedex.data.remote.responses.Pokemon
import com.example.composepokedex.data.remote.responses.PokemonList
import com.example.composepokedex.util.Resourse
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resourse<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resourse.Error("An unknown error occured.")
        }
        return Resourse.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resourse<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resourse.Error("An unknown error occured.")
        }
        return Resourse.Success(response)
    }
}