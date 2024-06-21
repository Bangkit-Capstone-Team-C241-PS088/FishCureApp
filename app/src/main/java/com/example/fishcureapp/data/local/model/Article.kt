package com.example.fishcureapp.data.local.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article (
    val id:String?,
    val title:String,
    val description:String,
    val link:String,
    val author:String,
    val date:String,
    @DrawableRes val urlToImage:Int,


    ):Parcelable