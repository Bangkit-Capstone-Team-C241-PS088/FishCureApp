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
                val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString("email_registered", email)
                    apply()
                }
                registrationViewModel.register(email, password)
                binding.progressBar.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show()
            }
        }

        registrationViewModel.registrationResult.observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            if (result.success) {
                Toast.makeText(this, "Registration successful, please check your email for the OTP", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("email", binding.etEmail.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Registration failed: ${result.error}", Toast.LENGTH_SHORT).show()
            }
        }

        //setupViews()
    }

    //  private fun setupViews() {
    //      btn_register.setText(R.string.register)
    //      tv_btn_login_now.setText(R.string.login_now_btn)
    //      tv_btn_login_now.setOnClickListener {
    //          navigateToLogin()
    //      }

    //   btn_register.setOnClickListener {
    //       val email = et_username.text.toString()
    //        val password = et_password.text.toString()

    //          if (isValidEmail(email) && isValidPassword(password)) {
    //              registerUser(email, password)
    //          } else {
    //              showToast(getString(R.string.invalid_input))
    //           }
    //      }
    //   }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6 // Sesuaikan dengan kebutuhan Anda
    }

    private fun registerUser(email: String, password: String) {
        //     showProgressBar()

        //    authApi.register(email, password).enqueue(object : Callback<ApiResponse> {
        //        override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
        //             hideProgressBar()
        //             if (response.isSuccessful) {
        //                 val apiResponse = response.body()
        //                 if (apiResponse?.status == "success") {
        //                     showToast(getString(R.string.registration_successful))
        //                     navigateToLogin()
        //                 } else {
        //                     showToast(apiResponse?.message ?: getString(R.string.registration_failed))
        //                 }
        //             } else {
        //                 showToast(getString(R.string.registration_failed))
        //             }
        //        }

        //        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
        //             hideProgressBar()
        //             showToast(t.message ?: getString(R.string.registration_failed))
        //         }
        //     })
        // }

        //  private fun navigateToLogin() {
        //      startActivity(Intent(this, LoginActivity::class.java))
        //      finish()
        //  }

        //  private fun showProgressBar() {
        //       progress_bar.visibility = View.VISIBLE
        //   }

        //   private fun hideProgressBar() {
        //       progress_bar.visibility = View.GONE
        //   }

        //  private fun showToast(message: String) {
        //      Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        //  }
    }
}
