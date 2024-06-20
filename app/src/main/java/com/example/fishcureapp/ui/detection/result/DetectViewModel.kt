package com.example.fishcureapp.ui.detection.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.data.DetectRepository
import com.example.fishcureapp.data.network.response.PredictResponse
import kotlinx.coroutines.launch
import java.io.File


class DetectViewModel(private val detectRepository: DetectRepository):ViewModel() {

    private val _predictResult = MutableLiveData<Result<PredictResponse>>()
    val predictResult: LiveData<Result<PredictResponse>> get() = _predictResult

    fun predictDisease(imageFile: File) {
        viewModelScope.launch {
            try {
                val response = detectRepository.predictDisease(imageFile)
                if (response.isSuccessful) {
                    _predictResult.value = Result.success(response.body()!!)
                } else {
                    _predictResult.value = Result.failure(Exception("Prediction failed"))
                }
            } catch (e: Exception) {
                _predictResult.value = Result.failure(e)
            }
        }
    }

}