package com.example.thenewsapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://generativelanguage.googleapis.com/") // Base URL của Gemini API
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: GPTApiService = retrofit.create(GPTApiService::class.java)
}
