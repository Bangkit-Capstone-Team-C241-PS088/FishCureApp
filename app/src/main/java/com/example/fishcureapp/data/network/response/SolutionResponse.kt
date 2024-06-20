package com.example.fishcureapp.data.network.response

import com.google.gson.annotations.SerializedName


data class SolutionResponse (
    val status: String,
    val message: String,
    val data: SolutionResponseData
)


data class SolutionResponseData (
    val data: DataSolution
)


data class DataSolution (
    val name: String,
    val gejala: Map<String, String>,

    @SerializedName("langkah_penanganan")
    val langkahPenanganan: Map<String, String>,

    val obat: Map<String, String>
)