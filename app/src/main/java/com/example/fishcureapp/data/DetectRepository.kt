package com.example.fishcureapp.data

import com.example.fishcureapp.data.network.api.DetectService
import com.example.fishcureapp.data.network.response.PredictResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class DetectRepository (
    private val detectService: DetectService
){

    suspend fun predictDisease(imageFile: File): Response<PredictResponse> {
        val requestBody = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("file", imageFile.name, requestBody)
        return detectService.predict(multipartBody)
    }

}