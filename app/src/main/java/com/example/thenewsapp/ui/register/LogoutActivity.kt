package com.example.thenewsapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LogoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Xóa thông tin phiên đăng nhập trong SQLite
        val dbHelper = DatabaseHelper(this)
        dbHelper.clearSession()  // Bạn cần có phương thức này để xóa session

        // Chuyển hướng về màn hình đăng nhập
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
