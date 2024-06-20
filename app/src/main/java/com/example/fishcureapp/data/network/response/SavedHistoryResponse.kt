package com.example.fishcureapp.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SavedHistoryResponse (
    val status: String,
    val message: String,
    val data: Map<String, SavedDataHistory>?
)

@Parcelize
data class SavedDataHistory (
    val email: String,

    @SerializedName("date_time")
    val dateTime: String,

    @SerializedName("disease_name")
    val disease_name: String,

    val akurasi: String,


    val image:String
): Parcelable
