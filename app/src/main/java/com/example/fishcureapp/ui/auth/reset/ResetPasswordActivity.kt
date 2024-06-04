package com.example.fishcureapp.ui.auth.reset

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.fishcureapp.data.factory.ViewModelFactory
import com.example.fishcureapp.databinding.ActivityResetPasswordBinding
import com.example.fishcureapp.ui.auth.login.LoginActivity

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private val resetPasswordViewModel: ResetPassViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val newPassword = binding.etNewPassword.text.toString()
            if (email.isNotEmpty() && newPassword.isNotEmpty()) {
                resetPasswordViewModel.resetPassword(email, newPassword)
            } else {
                Toast.makeText(this, "Please fill in the required fields", Toast.LENGTH_SHORT).show()
            }
        }

        resetPasswordViewModel.resetPasswordResult.observe(this) { result ->
            if (result.success) {
                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Failed to update password: ${result.error}", Toast.LENGTH_SHORT).show()
                // Handle password update failure
            }
        }
    }
}