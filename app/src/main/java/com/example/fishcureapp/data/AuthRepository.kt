package com.example.fishcureapp.data

import androidx.lifecycle.LiveData
import com.example.fishcureapp.data.network.api.ApiService
import com.example.fishcureapp.data.network.request.LoginRequest
import com.example.fishcureapp.data.local.UserPreference
import com.example.fishcureapp.data.local.historydao.HistoryDao
import com.example.fishcureapp.data.local.model.History
import com.example.fishcureapp.data.local.model.User
import com.example.fishcureapp.data.network.request.ArticleRequest
import com.example.fishcureapp.data.network.request.AuthOtpRequest
import com.example.fishcureapp.data.network.request.GetHistoryRequest
import com.example.fishcureapp.data.network.request.RegisterRequest
import com.example.fishcureapp.data.network.request.SendOtpRequest
import com.example.fishcureapp.data.network.request.SolutionRequest
import com.example.fishcureapp.data.network.request.UpdatePassRequest
import com.example.fishcureapp.data.network.response.ApiResponse
import com.example.fishcureapp.data.network.response.ArticleResponse
import com.example.fishcureapp.data.network.response.HistoryResponse
import com.example.fishcureapp.data.network.response.SavedHistoryResponse
import com.example.fishcureapp.data.network.response.SolutionResponseData
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class AuthRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val dao : HistoryDao
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

    suspend fun diseaseSolution(diseaseName:String): Response<SolutionResponseData> {
        val solutionRequest = SolutionRequest(diseaseName)
        return apiService.solution(solutionRequest)

    }

    suspend fun getArticle(email: String): Response<ArticleResponse> {
        val articleRequest = ArticleRequest(email)
        return apiService.getArticle(articleRequest)
    }

    suspend fun insertHistory(newHistory: History): Long{
        return dao.insertTask(newHistory)
    }

    suspend fun saveHistory(email: String, diseaseName: String, akurasi: Double, imageFile: File): Response<HistoryResponse> {
        val emailRequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val diseaseNameRequestBody = diseaseName.toRequestBody("text/plain".toMediaTypeOrNull())
        val akurasiRequestBody = akurasi.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val requestFile = imageFile.asRequestBody("image/png".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

        return apiService.saveHistory(emailRequestBody, diseaseNameRequestBody, akurasiRequestBody, imagePart)
    }

    fun getAllHistory(): LiveData<List<History>> {
        return dao.getAllHistory()
    }

    suspend fun getHistory(email:String):Response<SavedHistoryResponse> {
        val historyRequest = GetHistoryRequest(email)
        return apiService.getHistory(historyRequest)
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
