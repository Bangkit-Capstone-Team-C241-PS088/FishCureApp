package com.example.fishcureapp.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fishcureapp.data.factory.ViewModelFactory
import com.example.fishcureapp.databinding.ActivityRegisterBinding
import com.example.fishcureapp.ui.auth.login.LoginActivity
import com.example.fishcureapp.ui.auth.otp.OtpActivity
class RegisterActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registrationViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBtnLoginNow.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                registrationViewModel.register(email, password)
                binding.progressBar.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        registrationViewModel.registrationResult.observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            if (result.success) {
                Toast.makeText(
                    this,
                    "Registration successful, please check your email for the OTP",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("email", binding.etEmail.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Registration failed: ${result.error}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}