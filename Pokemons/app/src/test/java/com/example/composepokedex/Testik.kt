package com.example.composepokedex

import android.os.Bundle
import androidx.activity.testing.ActivityScenarioRule
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.testing.launchFragmentInHiltContainer
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.example.composepokedex.pokemondetail.PokemonDetailScreen
import com.example.composepokedex.pokemonlist.PokemonListScreen
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Testik {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun `firstTest - check PokemonDetailScreen navigation with correct arguments`() {
        val navController = TestNavHostController(activityScenarioRule.scenario.result.context)
        activityScenarioRule.scenario.onActivity { activity ->
            activity.setContent {
                val dominantColor = Color(0xFFFFFF)
                val pokemonName = "pikachu"
                PokemonDetailScreen(dominantColor, pokemonName, navController)
            }
            navController.navigate("pokemon_detail_screen/16777215/pikachu") // 0xFFFFFF as Int
            val currentDestination = navController.currentBackStackEntry?.destination?.route
            assert(currentDestination == "pokemon_detail_screen/16777215/pikachu")
        }
    }

    @Test
    fun `secondTest - check navigation with default color when null`() {
        val navController = TestNavHostController(activityScenarioRule.scenario.result.context)
        activityScenarioRule.scenario.onActivity { activity ->
            activity.setContent {
                val pokemonName = "charmander"
                PokemonDetailScreen(Color.White, pokemonName, navController)
            }
            navController.navigate("pokemon_detail_screen/4294967295/charmander") // 0xFFFFFF as Int
            val currentDestination = navController.currentBackStackEntry?.destination?.route
            assert(currentDestination == "pokemon_detail_screen/4294967295/charmander")
        }
    }

    @Test
    fun `thirdTest - check PokemonDetailScreen is opened`() {
        val navController = TestNavHostController(activityScenarioRule.scenario.result.context)
        activityScenarioRule.scenario.onActivity { activity ->
            activity.setContent {
                val dominantColor = Color(0xFF00FF00)
                val pokemonName = "bulbasaur"
                PokemonDetailScreen(dominantColor, pokemonName, navController)
            }
            navController.navigate("pokemon_detail_screen/4278255360/bulbasaur") // 0xFF00FF00 as Int
            val currentDestination = navController.currentBackStackEntry?.destination?.route
            assert(currentDestination == "pokemon_detail_screen/4278255360/bulbasaur")
        }
    }
}
