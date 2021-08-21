package com.somecompose.composepokeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.somecompose.composepokeapp.pokelist.PokeListScreen
import com.somecompose.composepokeapp.ui.theme.ComposePokeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePokeAppTheme {
                //Inst Ref
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "poke_list_screen"
                ) {
                    composable("poke_list_screen") {
                        PokeListScreen(navController = navController)
                    }
                    composable(
                        "poke_detail_screen/{pokeName}",
                        arguments = listOf(
                            navArgument("pokeName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val pokeName = remember {
                            it.arguments?.getString("pokeName")
                        }
                    }
                }
            }
        }
    }
}
