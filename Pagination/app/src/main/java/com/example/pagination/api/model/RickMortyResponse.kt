package com.example.pagination.api.model

import com.google.gson.annotations.SerializedName

data class RickMortyResponse(
    @SerializedName("info") val info: Info?,
    @SerializedName("results") val results : List<Character>?,
)

data class Info(
    @SerializedName("count") val totalItems: Int?,
    @SerializedName("pages") val totalPages: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?,
)

data class Character(
    @SerializedName("id") val id: Long?,
    @SerializedName("count") var totalItems: Int = 0,
    @SerializedName("pages") var totalPages: Int = 0,
    @SerializedName("name") val name: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("species") val species: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("image") val image: String?,
)