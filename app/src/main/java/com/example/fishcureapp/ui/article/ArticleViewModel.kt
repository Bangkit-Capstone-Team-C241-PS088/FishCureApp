package com.example.fishcureapp.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.data.network.response.DataArticle
import com.example.fishcureapp.data.network.response.DataSolution
import kotlinx.coroutines.launch

class ArticleViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _articleResult = MutableLiveData<Result<List<DataArticle>>>()
    val articleResult: LiveData<Result<List<DataArticle>>> = _articleResult

    fun getArticles(email: String) {
        viewModelScope.launch {
            try {
                val response = repository.getArticle(email)
                if (response.isSuccessful) {
                    response.body()?.data?.values?.toList()?.let {
                        _articleResult.value = Result.success(it)
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