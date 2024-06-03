package com.example.fishcureapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.fishcureapp.ui.auth.RegisterActivity
import com.example.fishcureapp.ui.repository.AuthRepository

class MainActivity : AppCompatActivity() {

    private val authRepository = AuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)

        // Contoh penggunaan fungsi register
        authRepository.register("test@example.com", "password123") { response ->
            if (response != null && response.status == "success") {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, response?.message ?: "Request failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Contoh penggunaan fungsi login
        authRepository.login("test@example.com", "password123") { response ->
            if (response != null && response.status == "success") {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, response?.message ?: "Request failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Implementasikan sendOtp, authOtp, dan updatePassword dengan cara serupa
    }
}
