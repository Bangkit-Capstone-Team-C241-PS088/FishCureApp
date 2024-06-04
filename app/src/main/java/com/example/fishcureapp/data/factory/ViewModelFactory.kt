package com.example.fishcureapp.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fishcureapp.data.di.Injection
import com.example.fishcureapp.ui.auth.login.LoginViewModel
import com.example.fishcureapp.ui.auth.reset.ResetPassViewModel
import com.example.fishcureapp.ui.auth.otp.OtpViewModel
import com.example.fishcureapp.ui.auth.register.RegisterViewModel
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.ui.home.HomeViewModel
import com.example.fishcureapp.ui.profile.ProfileViewModel

class ViewModelFactory (private val repository: AuthRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ResetPassViewModel::class.java) -> {
                ResetPassViewModel(repository) as T
            }
            modelClass.isAssignableFrom(OtpViewModel::class.java) -> {
                OtpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).also { instance = it }
            }
        }
    }

}