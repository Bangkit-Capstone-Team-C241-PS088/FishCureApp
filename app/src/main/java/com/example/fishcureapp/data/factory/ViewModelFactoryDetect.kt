package com.example.fishcureapp.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fishcureapp.data.DetectRepository
import com.example.fishcureapp.data.di.InjectDetect
import com.example.fishcureapp.ui.detection.result.DetectViewModel

class ViewModelFactoryDetect (private val repository: DetectRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetectViewModel::class.java) -> {
                DetectViewModel(repository) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        private var instance: ViewModelFactoryDetect? = null

        fun getInstance(context: Context): ViewModelFactoryDetect {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactoryDetect(InjectDetect.provideRepository(context)).also { instance = it }
            }
        }
    }

}