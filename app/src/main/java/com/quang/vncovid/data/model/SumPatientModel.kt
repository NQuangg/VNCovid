package com.quang.vncovid.data.model

import com.google.gson.annotations.SerializedName

data class SumPatientResponse(
    val success: Boolean, val list: List<NewsModel>
)

data class SummPatientModel(
    @SerializedName("Id") val id: Int,
    @SerializedName("Confirmed") val confirmed: Int,
    @SerializedName("Recovered") val recovered: Int,
    @SerializedName("Death") val death: Int,
    @SerializedName("PlusConfirmed") val plusConfirmed: Int,
    @SerializedName("PlusRecovered") val plusRecovered: Int,
    @SerializedName("PlusDeath") val plusDeath: Int,
)
