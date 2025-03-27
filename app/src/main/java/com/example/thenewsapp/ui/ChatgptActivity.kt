package com.example.thenewsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.thenewsapp.R
import com.example.thenewsapp.databinding.ActivityChatgptBinding
import com.example.thenewsapp.models.Content
import com.example.thenewsapp.models.GPTRequest
import com.example.thenewsapp.models.GPTResponse
import com.example.thenewsapp.models.Part
import com.example.thenewsapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatgptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatgptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatgptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonChatgpt.setOnClickListener {
            val userMessage = binding.editTextMessage.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                callGPT(userMessage)
            } else {
                Toast.makeText(this, "Please enter message!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun addMessage(message: String, isUser: Boolean) {
        val inflater = LayoutInflater.from(this)
        val messageLayout = if (isUser) {
            inflater.inflate(R.layout.message_user, binding.messagesContainer, false)
        } else {
            inflater.inflate(R.layout.message_bot, binding.messagesContainer, false)
        }

        val messageTextView = if (isUser) {
            messageLayout.findViewById<TextView>(R.id.userMessageTextView)
        } else {
            messageLayout.findViewById<TextView>(R.id.botMessageTextView)
        }

        messageTextView.text = message
        binding.messagesContainer.addView(messageLayout)
        binding.scrollView.fullScroll(View.FOCUS_DOWN)
    }

    private fun callGPT(userMessage: String) {
        Log.d("ChatgptActivity", "Calling GPT with message: $userMessage")
        addMessage(userMessage, true) // Add user message

        val request = GPTRequest(
            contents = listOf(Content(listOf(Part(userMessage))))
        )

        RetrofitClient.apiService.sendRequest(request).enqueue(object : Callback<GPTResponse> {
            override fun onResponse(call: Call<GPTResponse>, response: Response<GPTResponse>) {
                Log.d("ChatgptActivity", "Response Code: ${response.code()}")
                Log.d("ChatgptActivity", "Response Body: ${response.body()}")

                if (response.isSuccessful) {
                    val reply = response.body()?.candidates?.get(0)?.content?.parts?.get(0)?.text ?: "No response"
                    addMessage(reply, false) // Add bot reply
                } else {
                    addMessage("Error: ${response.errorBody()?.string()}", false)
                }
            }

            override fun onFailure(call: Call<GPTResponse>, t: Throwable) {
                Log.e("ChatgptActivity", "Request failed: ${t.message}")
                addMessage("Connection error\n: ${t.message}", false)
            }
        })
        binding.editTextMessage.text.clear() // Clear EditText after sending message
    }
}