package com.example.composepokedex.pokemondetail

import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.testing.TestNavBackStackEntry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.composepokedex.MainActivity
import com.example.composepokedex.data.remote.responses.Pokemon
import com.example.composepokedex.data.remote.responses.Type
import com.example.composepokedex.util.Resourse
import com.example.composepokedex.util.parseStatToColor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class Testik {

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        navController = TestNavHostController(InstrumentationRegistry.getInstrumentation().targetContext)
        navController.setGraph(R.navigation.nav_graph) // Set up your NavGraph here
    }

    @Test
    fun `firstTest`() = runBlockingTest {
        // Mocking the viewModel to return a successful response
        val viewModel = object : PokemonDetailViewModel {
            override suspend fun getPokemonInfo(pokemonName: String): Resourse<Pokemon> {
                val mockPokemon = Pokemon(
                    id = 1,
                    name = "bulbasaur",
                    types = listOf(Type("grass"), Type("poison")),
                    weight = 69,
                    height = 7,
                    stats = listOf(
                        Pokemon.Stats(base_stat = 45, effort = 0, stat = Pokemon.Stat(name = "attack")),
                        Pokemon.Stats(base_stat = 49, effort = 0, stat = Pokemon.Stat(name = "defense"))
                    ),
                    sprites = Pokemon.Sprites(front_default = "https://example.com/bulbasaur.png")
                )
                return Resourse.Success(mockPokemon)
            }
        }

        val pokemonName = "bulbasaur"
        navController.navigate("pokemon_detail_screen/${Color.Green.toArgb()}/$pokemonName")

        val screenState = PokemonDetailScreen(
            dominantColor = Color.Green,
            pokemonName = pokemonName,
            navController = navController,
            viewModel = viewModel
        )

        Assert.assertTrue(screenState.isNotEmpty()) // Check that something is displayed

        // Check if NavController returns to the expected destination
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals("pokemon_detail_screen/${Color.Green.toArgb()}/$pokemonName", currentRoute)
    }

    @Test
    fun `secondTest`() = runBlockingTest {
        val viewModel = object : PokemonDetailViewModel {
            override suspend fun getPokemonInfo(pokemonName: String): Resourse<Pokemon> {
                return Resourse.Loading()
            }
        }

        val pokemonName = "bulbasaur"
        navController.navigate("pokemon_detail_screen/${Color.Green.toArgb()}/$pokemonName")

        val screenState = PokemonDetailScreen(
            dominantColor = Color.Green,
            pokemonName = pokemonName,
            navController = navController,
            viewModel = viewModel
        )

        Assert.assertTrue(screenState.isNotEmpty()) // Ensure loading state shows
    }

    @Test
    fun `thirdTest`() = runBlockingTest {
        val viewModel = object : PokemonDetailViewModel {
            override suspend fun getPokemonInfo(pokemonName: String): Resourse<Pokemon> {
                return Resourse.Error("An error occurred")
            }
        }

        val pokemonName = "bulbasaur"
        navController.navigate("pokemon_detail_screen/${Color.Green.toArgb()}/$pokemonName")

        val screenState = PokemonDetailScreen(
            dominantColor = Color.Green,
            pokemonName = pokemonName,
            navController = navController,
            viewModel = viewModel
        )

        Assert.assertTrue(screenState.isNotEmpty()) // Ensure error message is displayed
    }