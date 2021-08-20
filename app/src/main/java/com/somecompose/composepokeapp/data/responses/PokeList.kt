package com.somecompose.composepokeapp.data.responses


data class PokeList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)
