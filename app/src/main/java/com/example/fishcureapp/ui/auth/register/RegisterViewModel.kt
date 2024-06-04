package com.example.fishcureapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.ui.auth.Result
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository):ViewModel() {

    private val _registrationResult = MutableLiveData<Result>()
    val registrationResult: LiveData<Result> get() = _registrationResult

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(email, password)
                if (response.isSuccessful) {
                    _registrationResult.value = Result(success = true)
                } else {
                    _registrationResult.value = Result(success = false, error = response.message())
                }
            } catch (e: Exception) {
                _registrationResult.value = Result(success = false, error = e.message.toString())
            }
        }
    }

}