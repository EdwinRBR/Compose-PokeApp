package com.somecompose.composepokeapp.pokelist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somecompose.composepokeapp.data.models.PokeListEntry
import com.somecompose.composepokeapp.repository.PokeRepository
import com.somecompose.composepokeapp.util.Constants.PAGE_SIZE
import com.somecompose.composepokeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val repository: PokeRepository
): ViewModel() {

    private var currentPage = 0

    var pokeList = mutableStateOf<List<PokeListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    // Backup data cache
    private var cachedPokeList = listOf<PokeListEntry>()
    private var isSearch = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokePaginated()
    }

    fun searchPokeList(query: String) {
        val listToSearch = if (isSearch) {
            pokeList.value
        } else {
            cachedPokeList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                // Reinitialize list
                pokeList.value = cachedPokeList
                isSearching.value = false
                isSearch = true

                return@launch
            }
            val results = listToSearch.filter {
                it.pokeName.contains(query.trim(), ignoreCase = true) || it.number.toString() == query.trim()
            }
            if (isSearch) {
                // Cache poke into CachePokeList
                cachedPokeList = pokeList.value
                isSearch = false
            }
            pokeList.value = results
            isSearching.value = true
        }
    }

    // Pagination Logic
    fun loadPokePaginated() {
        viewModelScope.launch {
            isLoading.value = true
            when(val result = repository.getPokeList(PAGE_SIZE, currentPage * PAGE_SIZE)) {
                is Resource.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.count
                    // Only cause API doesn't provide image url
                    val pokeEntries = result.data.results.mapIndexed { index, entry ->
                        // Remove "/" and take characters from the end as long is a Digit (ex: "/24/")
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokeListEntry(entry.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }, url, number.toInt())
                    }
                    currentPage++

                    loadError.value = ""
                    isLoading.value = false
                    pokeList.value += pokeEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }
}