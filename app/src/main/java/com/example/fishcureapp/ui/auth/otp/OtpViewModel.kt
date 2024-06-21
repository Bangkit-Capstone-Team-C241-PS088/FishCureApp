package com.example.fishcureapp.ui.auth.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.ui.auth.Result
import com.example.fishcureapp.data.AuthRepository
import kotlinx.coroutines.launch

class OtpViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _sendOtpResult = MutableLiveData<Result>()
    val sendOtpResult: LiveData<Result> get() = _sendOtpResult

    private val _verifyOtpResult = MutableLiveData<Result>()
    val verifyOtpResult: LiveData<Result> get() = _verifyOtpResult


    fun sendOtp(email: String) {
        viewModelScope.launch {
            try {
                val response = repository.sendOtp(email)
                if (response.isSuccessful) {
                    _sendOtpResult.value = Result(success = true)
                } else {
                    _sendOtpResult.value = Result(success = false, error = response.message())
                }
            } catch (e: Exception) {
                _sendOtpResult.value = Result(success = false, error = e.message.toString())
            }
        }
    }
    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            try {
                val response = repository.verifyOtp(email, otp)
                if (response.isSuccessful) {
                    _verifyOtpResult.value = Result(success = true)
                } else {
                    _verifyOtpResult.value = Result(success = false, error = response.message())
                }
            } catch (e: Exception) {
                _verifyOtpResult.value = Result(success = false, error = e.message.toString())
            }
        }
    }

}