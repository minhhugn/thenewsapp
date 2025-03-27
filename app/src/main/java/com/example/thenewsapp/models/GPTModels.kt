package com.example.thenewsapp.models

data class GPTRequest(
    val model: String = "gemini-2.0-flash",  // Model của Gemini
    val contents: List<Content>,        // Danh sách nội dung (thay vì messages)
    val generationConfig: GenerationConfig = GenerationConfig() // Thêm cấu hình thế hệ
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GenerationConfig(
    val temperature: Double = 0.7
)

data class GPTResponse(
    val candidates: List<Candidate> // Danh sách các phản hồi của AI
)

data class Candidate(
    val content: Content
)