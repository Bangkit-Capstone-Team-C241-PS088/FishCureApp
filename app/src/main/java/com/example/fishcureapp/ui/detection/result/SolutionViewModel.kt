package com.example.fishcureapp.ui.detection.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.data.network.response.DataHistory
import com.example.fishcureapp.data.network.response.DataSolution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class SolutionViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _solutionResult = MutableLiveData<Result<DataSolution>>()
    val solutionResult: LiveData<Result<DataSolution>> = _solutionResult

    private val _saveResult = MutableLiveData<Result<List<DataHistory>>>()
    val saveResult: LiveData<Result<List<DataHistory>>> = _saveResult

    fun getDiseaseSolution(diseaseName: String) {
        viewModelScope.launch {
            try {
                val response = repository.diseaseSolution(diseaseName)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        _solutionResult.postValue(Result.success(it))
                    } ?: run {
                        _solutionResult.postValue(Result.failure(Exception("No solution data available")))
                    }
                } else {
                    _solutionResult.postValue(Result.failure(Exception(response.message())))
                }
            } catch (e: Exception) {
                _solutionResult.postValue(Result.failure(e))
            }
        }
    }

    fun addHistory(email: String, diseaseName: String, akurasi: Double, imageFile: File) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.saveHistory(email, diseaseName, akurasi, imageFile)
                if (response.isSuccessful) {
                    response.body()?.data?.let { data ->
                        _saveResult.postValue(Result.success(listOf(data)))
                    } ?: run {
                        _saveResult.postValue(Result.failure(Exception("No API response data available")))
                    }
                } else {
                    _saveResult.postValue(Result.failure(Exception(response.message())))
                }
            } catch (e: Exception) {
                _saveResult.postValue(Result.failure(e))
            }
        }
    }
}