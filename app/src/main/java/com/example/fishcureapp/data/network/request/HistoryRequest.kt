package com.example.fishcureapp.data.network.request

import com.google.gson.annotations.SerializedName

data class HistoryRequest (

    @SerializedName("email")
    val email: String,

    @SerializedName("disease_name")
    val disease_name: String,

    @SerializedName("akurasi")
    val akurasi: String,

    @SerializedName("image")
    val image:String

)