package com.example.fishcureapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.ui.auth.Result
import kotlinx.coroutines.launch


class LoginViewModel(private val repository: AuthRepository):ViewModel() {

    private val _loginResult = MutableLiveData<Result>()
    val loginResult: LiveData<Result> get() = _loginResult



    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.isSuccessful) {
                    _loginResult.value = Result(success = true)
                } else {
                    _loginResult.value = Result(success = false, error = response.message())
                }
            } catch (e: Exception) {
                _loginResult.value = Result(success = false, error = e.message.toString())
            }
        }
    }

}