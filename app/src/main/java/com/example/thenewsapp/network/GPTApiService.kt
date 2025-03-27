package com.example.thenewsapp.network

import com.example.thenewsapp.models.GPTRequest
import com.example.thenewsapp.models.GPTResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface GPTApiService {
    @Headers(
        "Content-Type: application/json",
        "x-goog-api-key: AIzaSyB5h4XL0oIdGGO7QWpZYhm3R1XrjPMiwI4" // Thay thế YOUR_GEMINI_API_KEY
    )
    @POST("v1beta/models/gemini-2.0-flash:generateContent") // Thay đổi endpoint
    fun sendRequest(@Body request: GPTRequest): Call<GPTResponse>

    @Headers(
        "Content-Type: application/json",
        "x-goog-api-key: AIzaSyB5h4XL0oIdGGO7QWpZYhm3R1XrjPMiwI4" // Thay thế YOUR_GEMINI_API_KEY
    )
    @POST("v1beta/models/gemini-2.0-flash:generateContent") // Thay đổi endpoint
    fun getGeminiResponse(@Body requestBody: GPTRequest): Call<GPTResponse>
}