package com.example.fishcureapp.data.di

import android.content.Context
import com.example.fishcureapp.data.network.api.ApiConfig
import com.example.fishcureapp.data.AuthRepository
import com.example.fishcureapp.data.local.UserPreference
import com.example.fishcureapp.data.local.dataStore
import com.example.fishcureapp.data.local.historydao.HistoryDatabase
import com.example.fishcureapp.data.network.api.ApiConfigDetect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.provideApiService()
        val detectService = ApiConfigDetect.provideApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val dao =   HistoryDatabase.getDatabase(context).historyDao()

        return AuthRepository(apiService,pref, dao)
    }
}