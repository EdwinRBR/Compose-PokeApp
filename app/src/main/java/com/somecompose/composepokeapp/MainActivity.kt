package com.somecompose.composepokeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
                        "poke_detail_screen/{dominantColor}/{pokeName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokeName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokeName = remember {
                            it.arguments?.getString("pokeName")
                        }
                    }
                }
            }
        }
    }
}
