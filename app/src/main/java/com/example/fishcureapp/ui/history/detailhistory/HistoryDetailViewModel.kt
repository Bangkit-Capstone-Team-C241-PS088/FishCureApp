package com.example.fishcureapp.ui.history.detailhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.data.network.response.DataSolution
import kotlinx.coroutines.launch

class HistoryDetailViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _solutionResult = MutableLiveData<Result<DataSolution>>()
    val solutionResult: LiveData<Result<DataSolution>> = _solutionResult

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

}