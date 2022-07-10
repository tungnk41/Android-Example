package com.example.pagination.repository

import com.example.pagination.api.IApiRickMorty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RickMortyRepository @Inject constructor(private val apiRickMorty: IApiRickMorty){

    fun findAllCharacters(page: Int) = flow {
        emit(apiRickMorty.findAllCharacters(page)) }.flowOn(
        Dispatchers.IO)
}