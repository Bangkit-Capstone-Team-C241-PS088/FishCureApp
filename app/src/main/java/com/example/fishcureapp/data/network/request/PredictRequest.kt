package com.example.fishcureapp.data.network.request

data class PredictRequest (
    val diseaseName :String,
    val confidence :Double
)