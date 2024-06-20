package com.example.fishcureapp.ui.auth.otp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fishcureapp.data.factory.ViewModelFactory
import com.example.fishcureapp.databinding.ActivityOtpBinding
import com.example.fishcureapp.ui.auth.login.LoginActivity

class OtpActivity:AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding
    private val otpViewModel: OtpViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = this.getSharedPreferences("user_prefs", MODE_PRIVATE)
        val email = sharedPref.getString("email_registered", "") // Retrieve email from SharedPreference
        binding.etEmail.setText(email)
        //val email = intent.getStringExtra("email")

        binding.btnSendOtp.setOnClickListener {
            if (email != null) {
                otpViewModel.sendOtp(email)
                binding.progressBar.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Email is missing", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnVerifyOtp.setOnClickListener {
            val otp = binding.etOtp.text.toString()
            if (email != null && otp.isNotEmpty()) {
                otpViewModel.verifyOtp(email, otp)
                binding.progressBar.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Please enter the OTP", Toast.LENGTH_SHORT).show()
            }
        }

        otpViewModel.sendOtpResult.observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            if (result.success) {
                Toast.makeText(this, "OTP sent successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to send OTP: ${result.error}", Toast.LENGTH_SHORT).show()
            }
        }

        otpViewModel.verifyOtpResult.observe(this) { result ->
            binding.progressBar.visibility = View.GONE
            if (result.success) {
                Toast.makeText(this, "OTP verified successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "OTP verification failed: ${result.error}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}