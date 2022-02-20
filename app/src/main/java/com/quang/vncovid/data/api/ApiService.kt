package com.quang.vncovid.data.api

import com.quang.vncovid.data.model.ChartCovidResponse
import com.quang.vncovid.data.model.NewsResponse
import com.quang.vncovid.data.model.SumPatientResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("getArticles")
    suspend fun getNews(@Field("cateId") cateId: Int, @Field("pagination[pageSize]") pageSize: Int = 20): NewsResponse

    @POST("getSummPatient")
    suspend fun getSummPatient(): SumPatientResponse

    @POST("GetChartCovid")
    suspend fun getChartCovid(): ChartCovidResponse
}