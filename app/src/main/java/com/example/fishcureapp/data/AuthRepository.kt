package com.example.fishcureapp.data

import com.example.fishcureapp.data.api.AuthApi
import com.example.fishcureapp.data.request.LoginRequest
import com.example.fishcureapp.data.local.UserPreference
import com.example.fishcureapp.data.local.model.User
import com.example.fishcureapp.data.request.AuthOtpRequest
import com.example.fishcureapp.data.request.RegisterRequest
import com.example.fishcureapp.data.request.SendOtpRequest
import com.example.fishcureapp.data.request.UpdatePassRequest
import com.example.fishcureapp.ui.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AuthRepository(
    private val apiService: AuthApi,
    private val userPreference: UserPreference
) {

    suspend fun register(email: String, password: String): Response<ApiResponse> {
        val registerRequest = RegisterRequest(email,password)
        return apiService.register(registerRequest)
    }

    suspend fun login(email: String, password: String): Response<ApiResponse> {
        val loginRequest = LoginRequest(email, password)
        return apiService.login(loginRequest)
    }

    suspend fun sendOtp(email: String): Response<ApiResponse> {
        val sendOtpRequest = SendOtpRequest(email)
        return apiService.sendOtp(sendOtpRequest)
    }

    suspend fun verifyOtp(email: String, otp: String): Response<ApiResponse> {
        val authOtpRequest = AuthOtpRequest(email,otp)
        return apiService.verifyOtp(authOtpRequest)
    }

    suspend fun updatePassword(email: String, newPassword: String): Response<ApiResponse> {
        val updatePassRequest = UpdatePassRequest(email, newPassword)
        return apiService.updatePassword(updatePassRequest)
    }

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

}
