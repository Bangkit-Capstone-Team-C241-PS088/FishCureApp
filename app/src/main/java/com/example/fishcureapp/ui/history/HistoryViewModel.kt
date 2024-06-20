package com.example.fishcureapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.data.network.response.SavedDataHistory
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: AuthRepository): ViewModel() {

    private val _articleResult = MutableLiveData<Result<List<SavedDataHistory>>>()
    val articleResult: LiveData<Result<List<SavedDataHistory>>> = _articleResult

    fun getHistory(email: String) {
        viewModelScope.launch {
            try {
                val response = repository.getHistory(email)
                if (response.isSuccessful) {
                    response.body()?.data?.let { data ->
                        val dataList = data.values.toList()
                        _articleResult.value = Result.success(dataList)
                    } ?: run {
                        _articleResult.value = Result.failure(Exception("No articles available"))
                    }
                } else {
                    _articleResult.value = Result.failure(Exception(response.message()))
                }
            } catch (e: Exception) {
                _articleResult.value = Result.failure(e)
            }
        }
    }

}