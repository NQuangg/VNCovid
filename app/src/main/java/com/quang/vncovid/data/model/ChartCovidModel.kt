package com.quang.vncovid.data.model

data class ChartCovidResponse(
    val success: Boolean, val list: List<ChartCovidModel>
)

data class ChartCovidModel(
    val date: String,
    val confirmed: Int,
    val recovered: Int,
    val death: Int,
)
