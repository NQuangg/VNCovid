package com.quang.vncovid.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(val success: Boolean, val list: List<NewsModel>)

data class NewsModel(
    @SerializedName("Id") val id: Int,
    @SerializedName("Title") val title: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Url") val url: String,
    @SerializedName("ImageUrl") val imageUrl: String
)
