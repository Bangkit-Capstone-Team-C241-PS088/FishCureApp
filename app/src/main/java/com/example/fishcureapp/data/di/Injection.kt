package com.example.fishcureapp.data.di

import android.content.Context
import com.example.fishcureapp.data.api.ApiConfig
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.data.local.UserPreference
import com.example.fishcureapp.data.local.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.provideApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }

        return AuthRepository(apiService, pref)
    }
}