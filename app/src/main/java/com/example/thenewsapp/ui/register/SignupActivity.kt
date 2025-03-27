package com.example.thenewsapp.ui.register

import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.example.thenewsapp.R
import com.example.thenewsapp.databinding.ActivitySignupBinding
import android.content.Intent
//import android.os.Bundle
//import android.view.View
import android.widget.Toast

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var databaseHelper: DatabaseHelper

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.signupButton.setOnClickListener {
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val confirmPassword = binding.signupConfirm.text.toString()

            when {
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                    Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show()
                }
                password != confirmPassword -> {
                    Toast.makeText(this, "Invalid Password!", Toast.LENGTH_SHORT).show()
                }
                databaseHelper.checkEmail(email) -> {
                    Toast.makeText(this, "User already exists! Please login", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val insert = databaseHelper.insertData(email, password)
                    if (insert) {
                        Toast.makeText(this, "Signup Successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Toast.makeText(this, "Signup Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.loginRedirectText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
