package com.example.fishcureapp.ui.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("data")
    val data: Data? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("status")
    val status: String? = null
)

data class Data(
    @SerializedName("email")
    val email: String? = null
)
