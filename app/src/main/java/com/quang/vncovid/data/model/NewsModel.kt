package com.quang.vncovid.data.model

data class NewsResponse(val success: Boolean, val list: List<NewsModel>)

data class NewsModel(
    val Id: Int,
    val Title: String,
    val Description: String,
    val Url: String,
    val ImageUrl: String
)
