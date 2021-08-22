package com.somecompose.composepokeapp.ui.navigation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.somecompose.composepokeapp.R
import com.somecompose.composepokeapp.ui.screen.pokedetail.PokeDetailScreen
import com.somecompose.composepokeapp.ui.screen.pokelist.PokeListScreen
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun PokeNavigation() {

    //Inst Ref
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PokeSplash.route
    ) {
        composable(route = Screen.PokeSplash.route) {
            SplashScreen(navController = navController)
        }
        composable(
            route = Screen.PokeHome.route
        ) {
            PokeListScreen(navController = navController)
        }
        composable(
            route = Screen.PokeDetail.route,
            arguments = listOf(
                navArgument("pokeName") {
                    type = NavType.StringType
                }
            )
        ) {
            val pokeName = remember {
                it.arguments?.getString("pokeName")
            }
            PokeDetailScreen(
                pokeName = pokeName?.lowercase(Locale.ROOT) ?: "",
                navController = navController
            )
        }
    }
}

/*
  - May replace it with SplashScreen API
  - https://developer.android.com/about/versions/12/features/splash-screen
 */
@Composable
fun SplashScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val scale = remember {
            androidx.compose.animation.core.Animatable(0f)
        }
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(2f).getInterpolation(it)
                    }
                )
            )
            delay(2000L)
            navController.navigate(route = Screen.PokeHome.route)
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_poke),
                contentDescription = "Logo",
                modifier = Modifier
                    .scale(scale.value)
            )
        }
    }
}
