package com.example.fishcureapp.ui.auth.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.data.AuthRepository
import kotlinx.coroutines.launch
import com.example.fishcureapp.ui.auth.Result

class ResetPassViewModel(private val repository: AuthRepository): ViewModel() {

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

}