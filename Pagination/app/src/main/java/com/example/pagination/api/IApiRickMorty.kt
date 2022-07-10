package com.example.pagination.api

import com.example.pagination.api.model.RickMortyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiRickMorty {
    @GET("api/character/")
    suspend fun findAllCharacters(
        @Query("page") page: Int,
    ) : RickMortyResponse
}