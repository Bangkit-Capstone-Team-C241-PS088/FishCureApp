package com.example.fishcureapp.data.network.response

import com.google.gson.annotations.SerializedName


data class PredictResponse (
    @SerializedName("class")
    val detectResponseClass: String,

    val confidence: Double
)
