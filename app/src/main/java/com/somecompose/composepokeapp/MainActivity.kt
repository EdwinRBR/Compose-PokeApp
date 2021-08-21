package com.somecompose.composepokeapp

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
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
import com.somecompose.composepokeapp.pokedetail.PokeDetailScreen
import com.somecompose.composepokeapp.pokelist.PokeListScreen
import com.somecompose.composepokeapp.ui.theme.ComposePokeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePokeAppTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {

    //Inst Ref
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
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
            PokeDetailScreen(
                pokeName = pokeName?.lowercase(Locale.ROOT) ?: "",
                navController = navController
            )
        }
    }
}

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
            Animatable(0f)
        }
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing =  {
                        OvershootInterpolator(2f).getInterpolation(it)
                    }
                )
            )
            delay(2000L)
            navController.navigate("poke_list_screen")
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
