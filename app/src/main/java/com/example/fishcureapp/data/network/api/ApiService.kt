package com.example.fishcureapp.data.network.api

import com.example.fishcureapp.data.network.request.AuthOtpRequest
import com.example.fishcureapp.data.network.request.GetHistoryRequest
import com.example.fishcureapp.data.network.request.LoginRequest
import com.example.fishcureapp.data.network.request.RegisterRequest
import com.example.fishcureapp.data.network.request.SendOtpRequest
import com.example.fishcureapp.data.network.request.SolutionRequest
import com.example.fishcureapp.data.network.request.UpdatePassRequest
import com.example.fishcureapp.data.network.response.ApiResponse
import com.example.fishcureapp.data.network.response.ArticleResponse
import com.example.fishcureapp.data.network.response.HistoryResponse
import com.example.fishcureapp.data.network.response.SavedHistoryResponse
import com.example.fishcureapp.data.network.response.SolutionResponseData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

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

    @POST("solution")
    suspend fun solution(
        @Body solutionRequest: SolutionRequest
    ): Response<SolutionResponseData>

    @POST("getAllArticle")
    suspend fun getArticle(
        @Body articleRequest: ArticleRequest
    ): Response<ArticleResponse>

    @Multipart
    @POST("saveHistory")
    suspend fun saveHistory(
        @Part("email") email: RequestBody,
        @Part("disease_name") diseaseName: RequestBody,
        @Part("akurasi") akurasi: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<HistoryResponse>

    @POST("getAllHistory")
    suspend fun getHistory(
        @Body historyRequest: GetHistoryRequest
    ):Response<SavedHistoryResponse>

}