package com.example.notesapp.data.network

import com.example.notesapp.data.network.apiservice.RetrofitTaskieApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitTaskieInstance{
    private const val BASE_URL = "https://ada-taskie-backend.osc-fr1.scalingo.io/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val client = OkHttpClient.Builder().build()

    val api: RetrofitTaskieApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(RetrofitTaskieApiService::class.java)
    }

}