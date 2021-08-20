package com.somecompose.composepokeapp.data

import com.somecompose.composepokeapp.data.response.PokeList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokeList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : PokeList

    @GET("pokemon/{name}")
    suspend fun getPokeInfo(
        @Path("name") name: String
    ) : Pokemon
}