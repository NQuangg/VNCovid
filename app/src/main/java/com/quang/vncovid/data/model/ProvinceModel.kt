package com.quang.vncovid.data.model

data class ProvinceResponse(val success: Boolean, val list: List<ProvinceModel>)

data class ProvinceModel(
    val id: String,
    val title: String,
    val clsconfirmed: String,
    val clslevel: String,
    val confirmed: Int,
    val incconfirmed: Int,
    val deaths: Int,
    val donevaccine: Int,
    val onevaccinepercent: Float,
    val donevaccinepercent: Float,
)
