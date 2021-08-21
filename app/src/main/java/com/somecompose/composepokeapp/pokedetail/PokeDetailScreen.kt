package com.somecompose.composepokeapp.pokedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.somecompose.composepokeapp.data.responses.Pokemon
import com.somecompose.composepokeapp.util.Resource

@Composable
fun PokeDetailScreen(
    pokeName: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokeImageSize: Dp = 200.dp,
    viewModel: PokeDetailViewModel = hiltViewModel()
) {
    val pokeInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokeInfo(pokeName)
    }.value
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 16.dp)
    ) {
        PokeDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
            )
        PokeDetailStateWrapper(
            pokeInfo = pokeInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + pokeImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokeImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            )
        Box(contentAlignment = Alignment.TopCenter,
            modifier = Modifier
            .fillMaxSize()) {
            if (pokeInfo is Resource.Success) {
                pokeInfo.data?.sprites?.let {
                    Image(
                        painter = rememberImagePainter(
                            data = it.front_default,
                                builder = {
                                    crossfade(true)
                                }
                        ),
                    contentDescription = pokeInfo.data.name,
                    modifier = Modifier
                        .size(pokeImageSize)
                        .offset(y = topPadding))
                }
            }
        }
    }
}

@Composable
fun PokeDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Gray,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun PokeDetailStateWrapper(
    pokeInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {

    when(pokeInfo) {
        is Resource.Success -> {

        }
        is Resource.Error -> {
            Text(
                text = pokeInfo.message!!,
                color = Color.Red,
                modifier = modifier
                )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}