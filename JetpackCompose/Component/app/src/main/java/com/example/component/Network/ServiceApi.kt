package com.example.component.Network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ServiceApi {

    @Multipart
    @POST("backend/")
    fun upload(
        @Part("name") name: RequestBody,
        @Part("id")   id: RequestBody,
        @Part image: MultipartBody.Part
    ) : Response
}