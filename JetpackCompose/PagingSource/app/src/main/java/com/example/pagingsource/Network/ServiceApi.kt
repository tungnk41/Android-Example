package com.example.pagingsource.Network

import com.example.pagingsource.Model.Model
import com.example.pagingsource.Paging.PageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    @GET("character/")
    suspend fun getAll(@Query("page") page : Int) : Response<PageResponse<Model>>
}