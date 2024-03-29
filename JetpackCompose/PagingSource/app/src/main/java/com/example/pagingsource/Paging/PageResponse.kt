package com.example.pagingsource.Paging

import com.google.gson.annotations.SerializedName

data class PageResponse<T>(
    @SerializedName("info")
    val pageInfo : PageInfo,
    @SerializedName("results")
    val results: List<T> = listOf()
)

data class PageInfo(
    @SerializedName("count")
    val count : Int,
    @SerializedName("pages")
    val pages: Int,
    val next : String?,
    val prev : String?
)
