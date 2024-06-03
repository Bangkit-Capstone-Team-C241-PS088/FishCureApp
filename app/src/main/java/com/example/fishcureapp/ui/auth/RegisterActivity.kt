package com.example.fishcureapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fishcureapp.R
import com.example.fishcureapp.ui.api.AuthApi
import com.example.fishcureapp.ui.model.ApiResponse
import com.example.fishcureapp.ui.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity: AppCompatActivity() {

    private lateinit var authApi: AuthApi
    private lateinit var  btn_register: Button
    private lateinit var  tv_btn_login_now: TextView
    private lateinit var  et_username: EditText
    private lateinit var et_password: EditText
    private lateinit var progress_bar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        authApi = RetrofitClient.instance.create(AuthApi::class.java)

        setupViews()
    }

    private fun setupViews() {
        btn_register.setText(R.string.register)
        tv_btn_login_now.setText(R.string.login_now_btn)
        tv_btn_login_now.setOnClickListener {
            navigateToLogin()
        }

        btn_register.setOnClickListener {
            val email = et_username.text.toString()
            val password = et_password.text.toString()

            if (isValidEmail(email) && isValidPassword(password)) {
                registerUser(email, password)
            } else {
                showToast(getString(R.string.invalid_input))
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6 // Sesuaikan dengan kebutuhan Anda
    }

    private fun registerUser(email: String, password: String) {
        showProgressBar()

        authApi.register(email, password).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                hideProgressBar()
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse?.status == "success") {
                        showToast(getString(R.string.registration_successful))
                        navigateToLogin()
                    } else {
                        showToast(apiResponse?.message ?: getString(R.string.registration_failed))
                    }
                } else {
                    showToast(getString(R.string.registration_failed))
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                hideProgressBar()
                showToast(t.message ?: getString(R.string.registration_failed))
            }
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
