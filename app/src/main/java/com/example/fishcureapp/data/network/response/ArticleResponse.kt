package com.example.fishcureapp.data.network.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ArticleResponse(
    val status: String,
    val message: String,
    val data: Map<String, DataArticle>?
)

@Parcelize
data class DataArticle(
    val id_article: Int,
    val writer: String,
    val date_time: String,
    val image: String,
    val title: String,
    val source:String,
    val article: Map<String, String>
) : Parcelable