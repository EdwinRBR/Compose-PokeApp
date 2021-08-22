package com.somecompose.composepokeapp.ui.navigation

sealed class Screen(open val route: String = "") {
    object PokeSplash: Screen("splash_screen")
    object PokeHome: Screen("poke_list_screen")
    object PokeDetail: Screen("poke_detail_screen/{pokeName}")
}