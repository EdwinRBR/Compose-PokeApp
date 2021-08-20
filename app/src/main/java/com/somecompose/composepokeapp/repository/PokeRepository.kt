package com.somecompose.composepokeapp.repository

import com.somecompose.composepokeapp.data.PokeApi
import com.somecompose.composepokeapp.data.responses.PokeList
import com.somecompose.composepokeapp.data.responses.Pokemon
import com.somecompose.composepokeapp.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import kotlin.Exception

@ActivityScoped
class PokeRepository @Inject constructor(
    private val api: PokeApi) {

    suspend fun getPokeList(limit: Int, offset: Int) : Resource<PokeList> {
        val response = try {
            api.getPokeList(limit, offset)
        } catch(e: Exception) {
            return Resource.Error("Bad things happens..")
        }
        return Resource.Success(response)
    }

    suspend fun getPokeInfo(pokeName: String) : Resource<Pokemon> {
        val response = try {
            api.getPokeInfo(pokeName)
        } catch(e: Exception) {
            return Resource.Error("Bad things happens..")
        }
        return Resource.Success(response)
    }
}