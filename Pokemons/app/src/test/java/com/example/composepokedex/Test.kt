import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import com.example.composepokedex.data.remote.PokeApi
import com.example.composepokedex.data.remote.responses.PokemonList
import com.example.composepokedex.repository.PokemonRepository
import com.example.composepokedex.util.Resourse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonRepositoryTest {

    private lateinit var repository: PokemonRepository
    private lateinit var api: PokeApi

    @Before
    fun setup() {
        // Create a PokeApi instance using Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(PokeApi::class.java)
        repository = PokemonRepository(api)
    }

    @Test
    fun `test getPokemonList returns error`() = runTest {
        // Simulate network error
        val exception = Exception("Network error")
        api = object : PokeApi {
            override suspend fun getPokemonList(limit: Int, offset: Int): PokemonList {
                throw exception
            }

            override suspend fun getPokemonInfo(name: String): Pokemon {
                throw exception
            }
        }

        repository = PokemonRepository(api)
        val result = repository.getPokemonList(limit = 20, offset = 0)

        // Assert
        assertTrue(result is Resourse.Error)
        assertEquals("An unknown error occurred.", (result as Resourse.Error).message)
    }

    @Test
    fun `test getPokemonInfo returns error`() = runTest {
        val exception = Exception("Pokemon not found")
        api = object : PokeApi {
            override suspend fun getPokemonList(limit: Int, offset: Int): PokemonList {
                throw exception
            }

            override suspend fun getPokemonInfo(name: String): Pokemon {
                throw exception
            }
        }

        repository = PokemonRepository(api)
        val result = repository.getPokemonInfo(pokemonName = "bulbasaur")

        // Assert
        assertTrue(result is Resourse.Error)
    }
}
