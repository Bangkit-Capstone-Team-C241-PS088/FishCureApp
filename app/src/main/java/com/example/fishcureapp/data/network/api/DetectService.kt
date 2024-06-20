package com.example.fishcureapp.data.network.api

import com.example.fishcureapp.data.network.response.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DetectService {

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part file: MultipartBody.Part
    ): Response<PredictResponse>

}