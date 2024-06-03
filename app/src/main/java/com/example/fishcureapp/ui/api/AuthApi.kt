package com.example.fishcureapp.ui.api

import com.example.fishcureapp.ui.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    @GET("register")
    fun register(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<ApiResponse>

    @GET("login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<ApiResponse>

    @GET("sendOtp")
    fun sendOtp(
        @Query("email") email: String
    ): Call<ApiResponse>

    @GET("authOtp")
    fun authOtp(
        @Query("email") email: String,
        @Query("otp") otp: String
    ): Call<ApiResponse>

    @GET("updatePassword")
    fun updatePassword(
        @Query("email") email: String,
        @Query("newPassword") newPassword: String
    ): Call<ApiResponse>
}