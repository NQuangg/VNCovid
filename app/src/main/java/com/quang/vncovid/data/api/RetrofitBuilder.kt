package com.quang.vncovid.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val BASE_URL = "https://emag.thanhnien.vn/covid19/home/"

private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)
