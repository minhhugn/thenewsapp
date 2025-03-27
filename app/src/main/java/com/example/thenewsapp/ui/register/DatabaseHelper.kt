package com.example.thenewsapp.ui.register

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE users (email TEXT PRIMARY KEY, password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun insertData(email: String, password: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("email", email)
            put("password", password)
        }
        val result = db.insert("users", null, contentValues)
        return result != -1L
    }

    fun checkEmail(email: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun checkEmailPassword(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", arrayOf(email, password))
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }
    fun clearSession() {
        val db = this.writableDatabase
        db.delete("users", null, null)  // Giả sử "users" là bảng chứa thông tin đăng nhập
        db.close()
    }


    companion object {
        private const val DATABASE_NAME = "RegisNews.db"
        private const val DATABASE_VERSION = 1
    }
}
