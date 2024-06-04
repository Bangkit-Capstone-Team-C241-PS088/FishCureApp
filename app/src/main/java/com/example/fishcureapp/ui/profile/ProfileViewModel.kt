package com.example.fishcureapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.ui.auth.Result
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AuthRepository):ViewModel() {

    private lateinit var email: String

    fun setEmail(email: String) {
        this.email = email
    }

    fun getEmail(): String {
        return email
    }

    private val emailObserver = MutableLiveData<String>()

    fun getEmailObserver(): LiveData<String> = emailObserver

    private val _resetPasswordResult = MutableLiveData<Result>()
    val resetPasswordResult: LiveData<Result> get() = _resetPasswordResult

    fun resetPassword(email: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val response = repository.updatePassword(email, newPassword)
                if (response.isSuccessful) {
                    _resetPasswordResult.value = Result(success = true)
                } else {
                    _resetPasswordResult.value = Result(success = false, error = response.message())
                }
            } catch (e: Exception) {
                _resetPasswordResult.value = Result(success = false, error = e.message.toString())
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}