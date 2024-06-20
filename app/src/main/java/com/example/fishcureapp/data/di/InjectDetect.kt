package com.example.fishcureapp.data.di

import android.content.Context
import com.example.fishcureapp.data.DetectRepository
import com.example.fishcureapp.data.network.api.ApiConfigDetect

object InjectDetect {
    fun provideRepository(context: Context): DetectRepository {

        val detectService = ApiConfigDetect.provideApiService()
        // val pref = UserPreference.getInstance(context.dataStore)
        // val user = runBlocking { pref.getSession().first() }

        return DetectRepository(detectService)
    }
}