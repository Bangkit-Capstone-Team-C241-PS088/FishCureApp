package com.example.fishcureapp.data.api

import com.example.fishcureapp.data.request.AuthOtpRequest
import com.example.fishcureapp.data.request.LoginRequest
import com.example.fishcureapp.data.request.RegisterRequest
import com.example.fishcureapp.data.request.SendOtpRequest
import com.example.fishcureapp.data.request.UpdatePassRequest
import com.example.fishcureapp.ui.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<ApiResponse>


    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<ApiResponse>

    @POST("sendOtp")
    suspend fun sendOtp(
        @Body sendOtpRequest: SendOtpRequest
    ): Response<ApiResponse>

    @POST("authOtp")
    suspend fun verifyOtp(
        @Body authOtpRequest: AuthOtpRequest
    ): Response<ApiResponse>

    @POST("updatePassword")
    suspend fun updatePassword(
        @Body updatePassRequest: UpdatePassRequest
    ): Response<ApiResponse>


}