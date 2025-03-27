package com.example.thenewsapp.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.thenewsapp.databinding.ActivityLoginBinding
import android.content.Intent
import android.widget.Toast
import com.example.thenewsapp.ui.NewsActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            when {
                email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
                }
                databaseHelper.checkEmailPassword(email, password) -> {
                    Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, NewsActivity::class.java))
                }
                else -> {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.signupRedirectText.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
