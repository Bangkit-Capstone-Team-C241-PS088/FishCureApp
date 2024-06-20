package com.example.fishcureapp.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fishcureapp.data.factory.ViewModelFactory
import com.example.fishcureapp.databinding.ActivityLoginBinding
import com.example.fishcureapp.ui.MenuActivity
import com.example.fishcureapp.ui.auth.register.RegisterActivity
import com.example.fishcureapp.ui.auth.reset.ResetPasswordActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEMail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString("email", email)
                    apply()
                }
                loginViewModel.login(email, password)
                binding.progressBar.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvBtnRegisterNow.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginViewModel.loginResult.observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            if (result.success) {

                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Login failed: ${result.error}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvForgetPass.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

    }



}

