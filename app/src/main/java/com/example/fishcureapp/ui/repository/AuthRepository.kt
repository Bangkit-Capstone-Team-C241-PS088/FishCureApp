package com.example.fishcureapp.ui.repository

import com.example.fishcureapp.ui.api.AuthApi
import com.example.fishcureapp.ui.model.ApiResponse
import com.example.fishcureapp.ui.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {
    private val api = RetrofitClient.instance.create(AuthApi::class.java)

    fun register(email: String, password: String, callback: (ApiResponse?) -> Unit) {
        api.register(email, password).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun login(email: String, password: String, callback: (ApiResponse?) -> Unit) {
        api.login(email, password).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

//    fun sendOtp(email: String, callback: (ApiResponse?) -> Unit) {
//        api.sendOtp(email).enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                callback(response.body())
//            }
//
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                callback(null)
//            }
//        })
//    }
//
//    fun authOtp(email: String, otp: String, callback: (ApiResponse?) -> Unit) {
//        api.authOtp(email, otp).enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                callback(response.body())
//            }
//
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                callback(null)
//            }
//        })
//    }
//
//    fun updatePassword(email: String, newPassword: String, callback: (ApiResponse?) -> Unit) {
//        api.updatePassword(email, newPassword).enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                callback(response.body())
//            }
//
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                callback(null)
//            }
//        })
//    }
}
