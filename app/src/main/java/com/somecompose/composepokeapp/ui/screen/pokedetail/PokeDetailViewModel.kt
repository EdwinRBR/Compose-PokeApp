package com.somecompose.composepokeapp.ui.screen.pokedetail

import androidx.lifecycle.ViewModel
import com.somecompose.composepokeapp.data.responses.Pokemon
import com.somecompose.composepokeapp.repository.PokeRepository
import com.somecompose.composepokeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokeDetailViewModel  @Inject constructor(
    private val repository: PokeRepository
) : ViewModel() {

    suspend fun getPokeInfo(pokeName: String): Resource<Pokemon> {
        return repository.getPokeInfo(pokeName)
    }
}